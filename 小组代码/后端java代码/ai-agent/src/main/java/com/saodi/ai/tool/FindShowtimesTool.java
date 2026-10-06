package com.saodi.ai.tool;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.po.Showtimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  工具：查场次。每行都带 free/sold，模型才有依据回答"哪场人少"。
 * </p>
 *
 * @author saodi
 */
@Component
public class FindShowtimesTool implements AgentTool {

    @Autowired
    private ShowtimeReader reader;

    @Override
    public String name() {
        return "find_showtimes";
    }

    @Override
    public String description() {
        return "按影院、影片、日期查具体场次。返回场次 id、片名、影院、影厅、日期、时间、票价，"
                + "以及该场的 seats/free/sold/damaged 座位统计。要选座或下单前必须先拿到这里的场次 id。";
    }

    @Override
    public Map<String, Object> parameters() {
        return Schemas.object(Schemas.props(
                "cinemaId", Schemas.prop("integer", "影院 id，可空"),
                "movieId", Schemas.prop("integer", "影片 id，可空"),
                "date", Schemas.prop("string", "日期 yyyy-MM-dd，可空"),
                "limit", Schemas.prop("integer", "返回几条，默认 20，最多 60")));
    }

    @Override
    public Object execute(Map<String, Object> args) {
        Integer cinemaId = Args.id(args, "cinemaId");
        Integer movieId = Args.id(args, "movieId");
        String date = Args.text(args.get("date"));
        int limit = Args.clamp(Args.integer(args.get("limit"), 20), 1, 60);

        QueryWrapper<Showtimes> wrapper = new QueryWrapper<>();
        if (cinemaId != null) {
            wrapper.eq("cinema_id", cinemaId);
        }
        if (movieId != null) {
            wrapper.eq("movie_id", movieId);
        }
        if (!date.isEmpty()) {
            wrapper.apply("DATE(showdate) = {0}", date);
        }
        wrapper.orderByAsc("showdate", "showtime");

        List<Showtimes> found = reader.list(wrapper);
        List<Map<String, Object>> rows = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, found.size()); i++) {
            rows.add(reader.describe(found.get(i)));
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("total", found.size());
        result.put("truncated", found.size() > rows.size());
        result.put("rows", rows);
        return result;
    }
}
