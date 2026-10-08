package com.saodi.ai.tool;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.po.Cinema;
import com.saodi.po.Showtimes;
import com.saodi.service.ICinemaService;
import com.saodi.service.IShowtimesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  工具：查影院，并带上每家影院实际有多少场。
 *  库里影院表有上百家是没有任何排片的，所以必须连场次数一起给，否则模型会推荐点进去是空的影院。
 * </p>
 *
 * @author saodi
 */
@Component
public class ListCinemasTool implements AgentTool {

    @Autowired
    private ICinemaService cinemaService;
    @Autowired
    private IShowtimesService showtimesService;

    @Override
    public String name() {
        return "list_cinemas";
    }

    @Override
    public String description() {
        return "查影院以及各自当前排了多少场。onlyWithShowtimes 为真时只返回有排片的影院。"
                + "排片为 0 的影院点进去是空的，不要推荐给用户。";
    }

    @Override
    public Map<String, Object> parameters() {
        return Schemas.object(Schemas.props(
                "keyword", Schemas.prop("string", "影院名片段，可为空"),
                "onlyWithShowtimes", Schemas.prop("boolean", "是否只返回有排片的影院，默认 true"),
                "limit", Schemas.prop("integer", "返回几条，默认 10，最多 40")));
    }

    @Override
    public Object execute(Map<String, Object> args) {
        String keyword = Args.text(args.get("keyword"));
        boolean onlyWithShowtimes = !"false".equalsIgnoreCase(Args.text(args.get("onlyWithShowtimes")));
        int limit = Args.clamp(Args.integer(args.get("limit"), 10), 1, 40);

        Map<Integer, Integer> counts = screeningCounts();

        List<Cinema> cinemas = findByKeyword(keyword);

        List<Map<String, Object>> rows = new ArrayList<>();
        List<Map<String, Object>> filteredOut = new ArrayList<>();
        int skipped = 0;
        for (Cinema cinema : cinemas) {
            int screenings = counts.getOrDefault(cinema.getId(), 0);
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", cinema.getId());
            row.put("name", cinema.getName() == null ? "" : cinema.getName().trim());
            row.put("city", cinema.getCity());
            row.put("address", cinema.getSpecifiedAddress());
            row.put("screenings", screenings);
            if (screenings == 0 && onlyWithShowtimes) {
                skipped++;
                filteredOut.add(row);
                continue;
            }
            rows.add(row);
        }
        rows.sort(Comparator.comparingInt((Map<String, Object> row) ->
                (Integer) row.get("screenings")).reversed());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("total", cinemas.size());
        result.put("withoutShowtimesSkipped", skipped);
        result.put("rows", rows.subList(0, Math.min(limit, rows.size())));
        // 空清单最容易被模型讲成「这家不存在」。这里把"名字对得上但一场没排"和"名字就没对上"分开说清楚，
        // 并把被过滤掉的那几家一并给它，它才能说「新乡万达影城（id 10237）目前没有排片」这种有据的话。
        if (rows.isEmpty() && !keyword.isEmpty()) {
            result.put("notice", filteredOut.isEmpty()
                    ? "库里没有名字包含「" + keyword + "」的影院，别把它当成有排片的影院推荐。"
                    : "名字匹配到 " + filteredOut.size() + " 家，但它们当前都没有排片，点进去是空的："
                            + names(filteredOut));
            if (!filteredOut.isEmpty()) {
                result.put("matchedWithoutShowtimes", filteredOut);
            }
        }
        return result;
    }

    private static String names(List<Map<String, Object>> rows) {
        StringBuilder text = new StringBuilder();
        for (Map<String, Object> row : rows) {
            if (text.length() > 0) {
                text.append("、");
            }
            text.append(row.get("name")).append("(id ").append(row.get("id")).append(")");
        }
        return text.toString();
    }

    /**
     * 用户嘴里的影院名和库里的名字往往不是一串：库里是「万达影城（新乡万达IMAX店）」，
     * 人说的是「新乡万达影城」，城市名和店名隔着括号，整串 LIKE 会落空。
     * 更糟的是库里还真有一行就叫「新乡万达影城」，而且一场排片都没有——整串命中它之后
     * 就轮不到切片匹配，模型拿到空清单会答「这家没有排片」，而用户问的那家恰恰是排片最多的那家。
     * 所以整串和两字切片两种匹配都查，合并去重，让模型看得见名字相近但真有排片的那家。
     */
    private List<Cinema> findByKeyword(String keyword) {
        if (keyword.isEmpty()) {
            return cinemaService.list(new QueryWrapper<Cinema>());
        }
        Map<Integer, Cinema> merged = new LinkedHashMap<>();
        for (Cinema cinema : cinemaService.list(new QueryWrapper<Cinema>().like("name", keyword))) {
            merged.put(cinema.getId(), cinema);
        }
        QueryWrapper<Cinema> loose = new QueryWrapper<>();
        for (String piece : pieces(keyword)) {
            loose.like("name", piece);
        }
        for (Cinema cinema : cinemaService.list(loose)) {
            merged.putIfAbsent(cinema.getId(), cinema);
        }
        return new ArrayList<>(merged.values());
    }

    private static List<String> pieces(String keyword) {
        List<String> pieces = new ArrayList<>();
        for (int i = 0; i + 2 <= keyword.length(); i += 2) {
            pieces.add(keyword.substring(i, i + 2));
        }
        if (pieces.isEmpty()) {
            pieces.add(keyword);
        }
        return pieces;
    }

    private Map<Integer, Integer> screeningCounts() {
        Map<Integer, Integer> counts = new LinkedHashMap<>();
        List<Map<String, Object>> grouped = showtimesService.listMaps(
                new QueryWrapper<Showtimes>().select("cinema_id AS cinemaId, COUNT(*) AS cnt").groupBy("cinema_id"));
        for (Map<String, Object> row : grouped) {
            Integer cinemaId = Args.integer(row.get("cinemaId"));
            Integer cnt = Args.integer(row.get("cnt"));
            if (cinemaId != null) {
                counts.put(cinemaId, cnt == null ? 0 : cnt);
            }
        }
        return counts;
    }
}
