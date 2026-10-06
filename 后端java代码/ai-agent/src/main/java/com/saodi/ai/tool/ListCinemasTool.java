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

        QueryWrapper<Cinema> wrapper = new QueryWrapper<>();
        if (!keyword.isEmpty()) {
            wrapper.like("name", keyword);
        }
        List<Cinema> cinemas = cinemaService.list(wrapper);

        List<Map<String, Object>> rows = new ArrayList<>();
        int skipped = 0;
        for (Cinema cinema : cinemas) {
            int screenings = counts.getOrDefault(cinema.getId(), 0);
            if (screenings == 0 && onlyWithShowtimes) {
                skipped++;
                continue;
            }
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", cinema.getId());
            row.put("name", cinema.getName() == null ? "" : cinema.getName().trim());
            row.put("city", cinema.getCity());
            row.put("address", cinema.getSpecifiedAddress());
            row.put("screenings", screenings);
            rows.add(row);
        }
        rows.sort(Comparator.comparingInt((Map<String, Object> row) ->
                (Integer) row.get("screenings")).reversed());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("total", cinemas.size());
        result.put("withoutShowtimesSkipped", skipped);
        result.put("rows", rows.subList(0, Math.min(limit, rows.size())));
        return result;
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
