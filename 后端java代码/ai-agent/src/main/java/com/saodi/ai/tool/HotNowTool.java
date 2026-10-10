package com.saodi.ai.tool;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.po.Movie;
import com.saodi.po.MovieHotSnapshot;
import com.saodi.po.Showtimes;
import com.saodi.service.IMovieHotSnapshotService;
import com.saodi.service.IMovieService;
import com.saodi.service.IShowtimesService;
import com.saodi.util.Titles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  工具：查当下热度榜。读的是站内缓存的外部榜单快照（movie_hot_snapshot），
 *  不在用户请求链路上打外网 —— 那是个非官方接口，随时会改，挂了也不能把智能体一起拖挂。
 * </p>
 *
 * @author saodi
 */
@Component
public class HotNowTool implements AgentTool {

    @Autowired
    private IMovieHotSnapshotService snapshots;
    @Autowired
    private IMovieService movieService;
    @Autowired
    private IShowtimesService showtimesService;

    @Override
    public String name() {
        return "hot_now";
    }

    @Override
    public String description() {
        return "查最近的外部热度榜（存在站内的快照，带抓取时间）。返回名次、片名、评分，"
                + "以及这部在站内对应哪部影片（libraryMovieId）和它一共有几场排片（showtimeCount）。"
                + "问「最近什么在映」「谁最热」「有什么新片」用这个工具。"
                + "available=false 表示外部源这次没抓到：直接说没通，"
                + "禁止改拿 list_movies 的 wantNumber 顶上来 —— 那个数存于 2024 年，"
                + "报出去是一份两年前的榜单还当成今天的。";
    }

    @Override
    public Map<String, Object> parameters() {
        return Schemas.object(Schemas.props(
                "limit", Schemas.prop("integer", "返回几条，默认 10，最多 20")));
    }

    @Override
    public Object execute(Map<String, Object> args) {
        int limit = Args.clamp(Args.integer(args.get("limit"), 10), 1, 20);

        Map<String, Object> result = new LinkedHashMap<>();
        List<Map<String, Object>> rows = new ArrayList<>();
        result.put("rows", rows);

        List<MovieHotSnapshot> batch = snapshots.latestBatch();
        if (batch.isEmpty()) {
            result.put("available", false);
            result.put("notice", "外部热度榜一次都没抓成功过（或快照被清了）。"
                    + "这就直接告诉用户，别用站内的想看数或评分编一份榜单出来。");
            return result;
        }

        Map<String, Movie> byTitle = libraryIndex();
        Map<Integer, Integer> showtimeCounts = showtimeCounts();

        for (int i = 0; i < Math.min(limit, batch.size()); i++) {
            MovieHotSnapshot item = batch.get(i);
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("rank", item.getRankNo());
            row.put("title", item.getTitle());
            row.put("rate", item.getRate() == null || item.getRate().isEmpty() ? "暂无" : item.getRate());

            Movie owned = byTitle.get(Titles.normalize(item.getTitle()));
            if (owned == null) {
                row.put("libraryMovieId", null);
                row.put("showtimeCount", 0);
            } else {
                row.put("libraryMovieId", owned.getId());
                row.put("showtimeCount", showtimeCounts.getOrDefault(owned.getId(), 0));
            }
            rows.add(row);
        }

        result.put("available", true);
        result.put("source", batch.get(0).getSource());
        result.put("fetchedAt", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(batch.get(0).getFetchedAt()));
        result.put("total", batch.size());
        result.put("truncated", batch.size() > rows.size());
        result.put("notice", "这是外部榜单，不代表站内每部都有票：只有 libraryMovieId 非空且 "
                + "showtimeCount 大于 0 的才可以在本站选座，要具体场次再拿这个 id 调 find_showtimes。");
        return result;
    }

    private Map<String, Movie> libraryIndex() {
        Map<String, Movie> index = new HashMap<>();
        for (Movie movie : movieService.list()) {
            // 站内片名有历史脏前缀，比对口径统一走 Titles
            String key = Titles.normalize(movie.getName());
            if (!key.isEmpty()) {
                index.put(key, movie);
            }
        }
        return index;
    }

    private Map<Integer, Integer> showtimeCounts() {
        Map<Integer, Integer> counts = new HashMap<>();
        QueryWrapper<Showtimes> wrapper = new QueryWrapper<>();
        wrapper.select("movie_id", "COUNT(*) AS c").groupBy("movie_id");
        for (Map<String, Object> row : showtimesService.listMaps(wrapper)) {
            Object movieId = row.get("movie_id");
            Object count = row.get("c");
            if (movieId instanceof Number && count instanceof Number) {
                counts.put(((Number) movieId).intValue(), ((Number) count).intValue());
            }
        }
        return counts;
    }
}
