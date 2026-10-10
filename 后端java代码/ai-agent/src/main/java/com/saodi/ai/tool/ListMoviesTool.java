package com.saodi.ai.tool;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.po.Movie;
import com.saodi.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  工具：查影片。
 * </p>
 *
 * @author saodi
 */
@Component
public class ListMoviesTool implements AgentTool {

    @Autowired
    private IMovieService movieService;

    @Override
    public String name() {
        return "list_movies";
    }

    @Override
    public String description() {
        return "查库里的影片。keyword 传片名片段可以模糊找；不传就按想看人数给最近的片子。"
                + "返回 id、片名、评分、时长、地区、上映日期、想看人数。要选场次前先拿这里的 id。"
                + "但 wantNumber 和 score 是 2024 年入库时存的一版，只能当站内档案，"
                + "不能当成现在的热度——问最近谁最热用 hot_now。";
    }

    @Override
    public Map<String, Object> parameters() {
        return Schemas.object(Schemas.props(
                "keyword", Schemas.prop("string", "片名片段，可为空"),
                "limit", Schemas.prop("integer", "返回几条，默认 10，最多 30")));
    }

    @Override
    public Object execute(Map<String, Object> args) {
        String keyword = Args.text(args.get("keyword"));
        int limit = Args.clamp(Args.integer(args.get("limit"), 10), 1, 30);

        QueryWrapper<Movie> wrapper = new QueryWrapper<>();
        if (!keyword.isEmpty()) {
            wrapper.like("name", keyword);
        }
        List<Movie> movies = movieService.list(wrapper);
        movies.sort(Comparator.comparingInt((Movie movie) ->
                movie.getWantNumber() == null ? 0 : movie.getWantNumber()).reversed());

        List<Map<String, Object>> rows = new ArrayList<>();
        for (Movie movie : movies.subList(0, Math.min(limit, movies.size()))) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", movie.getId());
            row.put("name", movie.getName() == null ? "" : movie.getName().trim());
            row.put("score", movie.getScore());
            row.put("length", movie.getMovieLength());
            row.put("region", movie.getRegion());
            row.put("releaseTime", movie.getReleaseTime() == null
                    ? "" : new SimpleDateFormat("yyyy-MM-dd").format(movie.getReleaseTime()));
            row.put("wantNumber", movie.getWantNumber());
            rows.add(row);
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("total", movies.size());
        result.put("rows", rows);
        return result;
    }
}
