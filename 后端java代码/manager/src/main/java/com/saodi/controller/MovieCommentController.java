package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.Movie;
import com.saodi.po.MovieComment;
import com.saodi.po.User;
import com.saodi.query.MovieCommentQuery;
import com.saodi.service.IMovieCommentService;
import com.saodi.service.IMovieService;
import com.saodi.service.IUserService;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@Api(value = "影评管理")
@RestController
@RequestMapping("/movie-comment")
public class MovieCommentController {

    @Autowired
    IMovieCommentService commentService;
    @Autowired
    IMovieService movieService;
    @Autowired
    IUserService userService;

    @ApiOperation("分页查询影评，可按影片名/用户名/评论内容过滤")
    @PostMapping("/page")
    public ResponseObj page(@RequestBody MovieCommentQuery query) {
        QueryWrapper<MovieComment> wrapper = new QueryWrapper<>();
        if (query.getMovieId() != null) {
            wrapper.eq("movie_id", query.getMovieId());
        }
        if (StringUtils.hasText(query.getMovieName())) {
            List<Integer> ids = movieService.list(new QueryWrapper<Movie>()
                    .select("id")
                    .like("name", query.getMovieName()))
                    .stream().map(Movie::getId).collect(Collectors.toList());
            if (ids.isEmpty()) {
                return ResponseObj.SUCCESS(emptyPage(query));
            }
            wrapper.in("movie_id", ids);
        }
        if (StringUtils.hasText(query.getUserName())) {
            List<Integer> ids = userService.list(new QueryWrapper<User>()
                    .select("id")
                    .like("user_name", query.getUserName()))
                    .stream().map(User::getId).collect(Collectors.toList());
            if (ids.isEmpty()) {
                return ResponseObj.SUCCESS(emptyPage(query));
            }
            wrapper.in("user_id", ids);
        }
        if (StringUtils.hasText(query.getContent())) {
            wrapper.like("content", query.getContent());
        }
        wrapper.orderByDesc("id");

        Page<MovieComment> page = commentService.page(
                new Page<MovieComment>(query.getCurrent(), query.getSize()), wrapper);
        attachDetails(page.getRecords());
        return ResponseObj.SUCCESS(page);
    }

    @ApiOperation("删除单条影评")
    @PostMapping("/del/{id}")
    public ResponseObj del(@PathVariable("id") Integer id) {
        MovieComment comment = commentService.getById(id);
        if (comment == null) {
            return ResponseObj.ERROR(502, "影评不存在");
        }
        commentService.removeById(id);
        commentService.refreshMovieScore(comment.getMovieId());
        return ResponseObj.SUCCESS("删除成功");
    }

    @ApiOperation("批量删除影评")
    @PostMapping("/batch")
    public ResponseObj delBatch(@RequestBody List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResponseObj.ERROR(503, "未选择要删除的影评");
        }
        Set<Integer> affected = commentService.listByIds(ids).stream()
                .map(MovieComment::getMovieId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        commentService.removeByIds(ids);
        affected.forEach(commentService::refreshMovieScore);
        return ResponseObj.SUCCESS("删除成功");
    }

    private Page<MovieComment> emptyPage(MovieCommentQuery query) {
        Page<MovieComment> empty = new Page<>(query.getCurrent(), query.getSize());
        empty.setRecords(Collections.emptyList());
        empty.setTotal(0);
        return empty;
    }

    /**
     * 列表回显用的作者与影片名，批量取，避免逐行查库。
     */
    private void attachDetails(List<MovieComment> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Set<Integer> userIds = list.stream()
                .map(MovieComment::getUserId).filter(Objects::nonNull).collect(Collectors.toSet());
        Set<Integer> movieIds = list.stream()
                .map(MovieComment::getMovieId).filter(Objects::nonNull).collect(Collectors.toSet());

        Map<Integer, User> users = new HashMap<>();
        if (!userIds.isEmpty()) {
            for (User user : userService.listByIds(userIds)) {
                user.setPassword(null);
                users.put(user.getId(), user);
            }
        }
        Map<Integer, String> movieNames = new HashMap<>();
        if (!movieIds.isEmpty()) {
            for (Movie movie : movieService.listByIds(movieIds)) {
                movieNames.put(movie.getId(), movie.getName());
            }
        }
        for (MovieComment comment : list) {
            comment.setUser(users.get(comment.getUserId()));
            comment.setMovieName(movieNames.get(comment.getMovieId()));
        }
    }
}
