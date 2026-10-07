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
        return "查具体场次。不带条件就是全部场次；cinemaId / movieId / date 都可以只填一部分；"
                + "用户点名问某一场时用 showtimeId 精确查，别在截断过的清单里找没找到就说不存在。"
                + "返回场次 id、片名、影院、影厅、日期、时间、票价，以及该场的 seats/free/sold/damaged 座位统计。"
                + "要选座或下单前必须先拿到这里的场次 id。"
                + "跨场次比较（哪场人最少、哪场最便宜）查这一趟就够：不带条件拿回全部场次，"
                + "每行都带 free/sold/price，自己排序给结论，别挨个调 seat_summary，也别因为用户没说影片就反问。";
    }

    @Override
    public Map<String, Object> parameters() {
        return Schemas.object(Schemas.props(
                "showtimeId", Schemas.prop("integer", "场次 id，只问某一场时填它，填了就走单场查询"),
                "cinemaId", Schemas.prop("integer", "影院 id，可空"),
                "movieId", Schemas.prop("integer", "影片 id，可空"),
                "date", Schemas.prop("string", "日期 yyyy-MM-dd，可空"),
                "limit", Schemas.prop("integer", "返回几条，默认 20，最多 60")));
    }

    @Override
    public Object execute(Map<String, Object> args) {
        Map<String, Object> result = new LinkedHashMap<>();
        List<Map<String, Object>> rows = new ArrayList<>();

        // 单场精确查。走 ShowtimeReader.find()：service 的 getById 是 INNER JOIN cinema/hall，
        // 那几条挂在已删影院上的场次（22/24/25/27）JOIN 不出来，但它自己的数据是全的，
        // 退成按主键直查就能答「场次27是《大雨》」，而不是照着一份截断的清单说「不存在」。
        Integer showtimeId = Args.id(args, "showtimeId");
        if (showtimeId != null) {
            Showtimes one = reader.find(showtimeId);
            result.put("total", one == null ? 0 : 1);
            if (one == null) {
                result.put("notice", "库里没有 id 为 " + showtimeId + " 的场次。");
                result.put("rows", rows);
                return result;
            }
            rows.add(reader.describe(one));
            result.put("rows", rows);
            return result;
        }

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
        for (int i = 0; i < Math.min(limit, found.size()); i++) {
            rows.add(reader.describe(found.get(i)));
        }

        result.put("total", found.size());
        result.put("rows", rows);
        // 截断这件事得说清楚，不然模型会把"没在这 20 行里"当成"库里没有"：
        // 全表就 21 场，默认 limit 20 按日期升序刚好把最后一场（2024-01-17 的 27）切掉。
        if (found.size() > rows.size()) {
            result.put("truncated", true);
            result.put("notice", "清单被 limit 截断，还有 " + (found.size() - rows.size())
                    + " 场没列出来。没在清单里出现不等于不存在，要确认某一场就带 showtimeId 再查一次。");
        } else {
            result.put("truncated", false);
        }
        return result;
    }
}
