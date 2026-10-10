package com.saodi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.po.Movie;
import com.saodi.po.MovieHotSnapshot;
import com.saodi.po.Showtimes;
import com.saodi.util.SeatMatrix;
import com.saodi.util.Titles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  外部热度榜的写入侧和读取侧。抓回来的东西一律落库，智能体和首页都只读库里的快照，
 *  没有一条用户请求会实时穿透到榜单源 —— 那是个非官方接口，挂了不能把站内一起拖挂。
 * </p>
 *
 * @author saodi
 */
@Service
public class HotMovieSyncService {

    /** 只留最近这么多个批次，够回答「刚刚那次抓的」就停了；这是缓存不是档案 */
    private static final int KEEP_BATCHES = 10;

    /**
     * 补进站内的片子往哪儿排：{影院 id, 影厅 id, 抄座位矩阵用的模板场次 id}。
     * 只挑真有影厅、也真排过片的两个影院（新乡万达和辉县万达），其余影院在库里一场都没有。
     * 模板避开场次 12/19/21：那三行的矩阵里有历史脏值 "p30"（前端类名漏进了数据库）。
     */
    private static final int[][] SLOTS = {
            {1, 1, 4}, {1, 2, 2}, {1, 3, 6}, {2, 19, 9}, {2, 20, 8}
    };
    private static final String[] TIMES = {"10:30:00", "14:00:00", "19:30:00", "21:00:00"};

    @Autowired
    private DoubanHotClient client;
    @Autowired
    private IMovieHotSnapshotService snapshots;
    @Autowired
    private IMovieService movieService;
    @Autowired
    private IShowtimesService showtimesService;

    /** 模板矩阵去掉已售之后的成品，按模板场次 id 缓存，一次补片不必反复读库 */
    private final Map<Integer, String> freshMatrices = new HashMap<>();

    /**
     * 抓一次并写成一个新批次。抓失败时一行都不写，于是读侧继续用上一批、
     * 时间戳还是上一批的，不会出现「榜单看着是今天的，其实是三年前那次没抓成的」。
     */
    public Map<String, Object> refresh(int limit) {
        List<DoubanHotClient.HotItem> items;
        try {
            items = client.fetch("热门", Math.max(1, Math.min(limit, 30)));
        } catch (DoubanHotClient.HotSourceException e) {
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("ok", false);
            result.put("reason", e.getMessage());
            result.put("kept", snapshots.latestBatch().size());
            return result;
        }

        Date now = new Date();
        // 批次号带毫秒：只到秒的话，同一秒里点两次刷新（或者启动自动抓和手动刷新撞上）
        // 会生成同一个 batch_id，撞上 uk_batch_rank 就把整批插失败了。
        String batchId = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS").format(now);
        List<MovieHotSnapshot> rows = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            DoubanHotClient.HotItem item = items.get(i);
            MovieHotSnapshot row = new MovieHotSnapshot();
            row.setBatchId(batchId);
            row.setSource("douban");
            row.setRankNo(i + 1);
            row.setTitle(item.getTitle());
            row.setRate(item.getRate() == null ? "" : item.getRate());
            row.setCover(item.getCover());
            row.setSubjectId(item.getSubjectId());
            row.setSubjectUrl(item.getUrl());
            row.setFetchedAt(now);
            rows.add(row);
        }
        snapshots.saveBatch(rows);
        prune();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("ok", true);
        result.put("batchId", batchId);
        result.put("count", rows.size());
        result.put("fetchedAt", now);
        return result;
    }

    /**
     * 首页在读这份快照：库里有的片会带上 movieId 并标 inLibrary，这样「最热」和「本站能买票」是两件事，
     * 别让读的人以为榜单上的每一部本站都有场。
     */
    public Map<String, Object> current() {
        List<MovieHotSnapshot> batch = snapshots.latestBatch();
        Map<String, Integer> owned = libraryTitleIds();

        List<Map<String, Object>> rows = new ArrayList<>();
        for (MovieHotSnapshot item : batch) {
            String key = Titles.normalize(item.getTitle());
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("rank", item.getRankNo());
            row.put("title", item.getTitle());
            row.put("rate", item.getRate());
            row.put("cover", item.getCover());
            row.put("subjectId", item.getSubjectId());
            row.put("movieId", owned.get(key));
            row.put("inLibrary", owned.containsKey(key));
            rows.add(row);
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("available", !rows.isEmpty());
        result.put("source", rows.isEmpty() ? "" : batch.get(0).getSource());
        // 抓取时间给前端的是格式化好的字符串：给 epoch 毫秒的话，页面上要么显示一串数字，
        // 要么由前端自己换算，而「这份榜单是什么时候抓的」这种话只能有一个说法。
        result.put("fetchedAt", rows.isEmpty() ? null
                : new SimpleDateFormat("yyyy-MM-dd HH:mm").format(batch.get(0).getFetchedAt()));
        result.put("count", rows.size());
        result.put("rows", rows);
        return result;
    }

    private void prune() {
        QueryWrapper<MovieHotSnapshot> probe = new QueryWrapper<>();
        probe.select("DISTINCT batch_id").orderByDesc("batch_id");
        List<Object> batches = snapshots.listObjs(probe);
        if (batches.size() <= KEEP_BATCHES) {
            return;
        }
        List<Object> stale = batches.subList(KEEP_BATCHES, batches.size());
        snapshots.remove(new QueryWrapper<MovieHotSnapshot>().in("batch_id", stale));
    }

    /**
     * 把榜单前几部「站内还没有」的片补成影片，并顺手排出未来一周的场次。
     *
     * 这一步是显式点出来的动作，不跟着刷新自动跑：外部榜单天天会变，
     * 而排片表是被人拿去卖票的，不能因为上游换了榜首就把场次改一遍。
     * 片名、评分、海报用外部源给的真值；时长、地区、上映日期、想看数榜单不给，
     * 一律留空而不是编一个数出来。
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> promote(int limit) {
        Map<String, Object> result = new LinkedHashMap<>();
        List<MovieHotSnapshot> batch = snapshots.latestBatch();
        if (batch.isEmpty()) {
            result.put("ok", false);
            result.put("reason", "还没有任何榜单快照，先刷新一次再补。");
            return result;
        }

        Map<String, Integer> existing = libraryTitleIds();
        List<String> added = new ArrayList<>();
        List<String> alreadyThere = new ArrayList<>();
        int created = 0;

        int take = Math.max(1, Math.min(limit, batch.size()));
        for (int i = 0; i < take; i++) {
            MovieHotSnapshot item = batch.get(i);
            String key = Titles.normalize(item.getTitle());
            if (existing.containsKey(key)) {
                alreadyThere.add(item.getTitle());
                continue;
            }
            Movie movie = new Movie();
            movie.setName(item.getTitle());
            movie.setBanner(item.getCover() == null ? "" : item.getCover());
            movie.setScore(toScore(item.getRate()));
            // 票价不写在影片上：站内算价一律读 showtimes.sale，movie 实体也没有 price 这个字段。
            movieService.save(movie);
            existing.put(key, movie.getId());
            added.add(item.getTitle());
            created += schedule(movie.getId(), added.size() - 1);
        }

        result.put("ok", true);
        result.put("added", added);
        result.put("alreadyThere", alreadyThere);
        result.put("showtimesCreated", created);
        result.put("snapshotFetchedAt", batch.get(0).getFetchedAt());
        return result;
    }

    /** 一部新片排 4 场，落在不同影厅和不同日期上，够演示「这周六晚上有场」就够了 */
    private int schedule(int movieId, int offset) {
        long dayMillis = 24L * 3600 * 1000;
        long today = startOfToday();
        List<Showtimes> rows = new ArrayList<>();
        for (int j = 0; j < TIMES.length; j++) {
            int[] slot = SLOTS[(offset + j) % SLOTS.length];
            String seat = freshMatrix(slot[2]);
            if (seat == null) {
                continue;
            }
            Showtimes row = new Showtimes();
            row.setMovieId(movieId);
            row.setCinemaId(slot[0]);
            row.setHallId(slot[1]);
            row.setShowtime(Time.valueOf(TIMES[j]));
            // 铺开十天而不是七天：从周六往后数七天数不到「下周六」，
            // 而「下周六有什么场」是这条链路上最常见的一句问法。
            row.setShowdate(new Date(today + dayMillis * ((offset + j) % 10)));
            // 档位是站内定的：IMAX 厅 35，辉县这家 20，其余普通厅 25
            row.setSale(BigDecimal.valueOf(slot[0] == 2 ? 20 : (slot[1] == 2 ? 35 : 25)));
            row.setSeat(seat);
            rows.add(row);
        }
        if (rows.isEmpty()) {
            return 0;
        }
        showtimesService.saveBatch(rows);
        return rows.size();
    }

    /**
     * 抄一个同厅模板场次的矩阵，把已售清成可选、把认不出的脏值写成 -2 损坏。
     * 新场次从全空开始才对得上现实：卖出去的才会变 1。
     * 脏值转成 -2 而不是留着，是因为 serialize 会把 Integer.MIN_VALUE 原样拼进字符串，
     * 那比 "p30" 更坏 —— 前端和后端都会读成一个大负数。
     */
    private String freshMatrix(int templateShowtimeId) {
        if (freshMatrices.containsKey(templateShowtimeId)) {
            return freshMatrices.get(templateShowtimeId);
        }
        String value = null;
        // 不走 service.getById()：那是后台页面用的联表查询（INNER JOIN cinema/hall/movie），
        // 只要有一边对不上就返回 null，而这里要的只是那一条 showtimes.seat 字符串本身。
        Showtimes template = showtimesService.getOne(
                new QueryWrapper<Showtimes>().eq("id", templateShowtimeId), false);
        if (template != null && template.getSeat() != null) {
            List<List<Integer>> grid = SeatMatrix.parse(template.getSeat());
            if (!grid.isEmpty()) {
                for (List<Integer> row : grid) {
                    for (int i = 0; i < row.size(); i++) {
                        int cell = row.get(i);
                        if (cell == SeatMatrix.VALUE_SOLD) {
                            row.set(i, SeatMatrix.VALUE_FREE);
                        } else if (cell == SeatMatrix.VALUE_UNKNOWN) {
                            row.set(i, SeatMatrix.VALUE_DAMAGED);
                        }
                    }
                }
                value = SeatMatrix.serialize(grid);
            }
        }
        freshMatrices.put(templateShowtimeId, value);
        return value;
    }

    private Map<String, Integer> libraryTitleIds() {
        Map<String, Integer> ids = new HashMap<>();
        for (Movie movie : movieService.list()) {
            String key = Titles.normalize(movie.getName());
            if (!key.isEmpty()) {
                ids.put(key, movie.getId());
            }
        }
        return ids;
    }

    private static Double toScore(String rate) {
        try {
            return rate == null || rate.trim().isEmpty() ? null : Double.valueOf(rate.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static long startOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
}
