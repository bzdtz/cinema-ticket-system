package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.po.Movie;
import com.saodi.po.UserSeeRecord;
import com.saodi.service.IMovieService;
import com.saodi.service.IUserSeeRecordService;
import com.saodi.util.CurrentUser;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@Api(value = "想看")
@RestController
@RequestMapping("/user-see-record")
public class UserSeeRecordController {

    @Autowired
    IUserSeeRecordService service;
    @Autowired
    IMovieService movieService;

    @ApiOperation("当前用户是否已标记想看")
    @GetMapping("/isWant/{movieId}")
    public ResponseObj isWant(@PathVariable("movieId") Integer movieId, HttpServletRequest request) {
        Integer uid = CurrentUser.id(request);
        if (uid == null) {
            return ResponseObj.ERROR(501, "登录验证失败");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("want", countOf(movieId, uid) > 0);
        return ResponseObj.SUCCESS(result);
    }

    /**
     * want_number 里存有 2023 年导入的真实想看人数，不能按本地记录重算，
     * 所以这里只做一次性增减；(user_id, movie_id) 的唯一键保证状态翻转只会发生一次。
     */
    @ApiOperation("切换想看状态，同步维护 movie.want_number")
    @PostMapping("/toggle/{movieId}")
    public ResponseObj toggle(@PathVariable("movieId") Integer movieId, HttpServletRequest request) {
        Integer uid = CurrentUser.id(request);
        if (uid == null) {
            return ResponseObj.ERROR(501, "登录验证失败");
        }
        Movie movie = movieService.getById(movieId);
        if (movie == null) {
            return ResponseObj.ERROR(502, "影片不存在");
        }

        boolean want;
        QueryWrapper<UserSeeRecord> own = new QueryWrapper<UserSeeRecord>()
                .eq("user_id", uid)
                .eq("movie_id", movieId);
        if (service.remove(own)) {
            want = false;
        } else {
            UserSeeRecord record = new UserSeeRecord();
            record.setUserId(uid);
            record.setMovieId(movieId);
            if (!service.save(record)) {
                return ResponseObj.ERROR(503, "操作失败");
            }
            want = true;
        }

        int current = movie.getWantNumber() == null ? 0 : movie.getWantNumber();
        int updated = want ? current + 1 : Math.max(current - 1, 0);
        movie.setWantNumber(updated);
        movieService.updateById(movie);

        Map<String, Object> result = new HashMap<>();
        result.put("want", want);
        result.put("wantNumber", updated);
        return ResponseObj.SUCCESS(result);
    }

    private long countOf(Integer movieId, Integer uid) {
        return service.count(new QueryWrapper<UserSeeRecord>()
                .eq("user_id", uid)
                .eq("movie_id", movieId));
    }
}
