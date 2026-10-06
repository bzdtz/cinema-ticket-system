package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.saodi.po.Movie;
import com.saodi.po.MovieComment;
import com.saodi.po.User;
import com.saodi.service.IMovieCommentService;
import com.saodi.service.IMovieService;
import com.saodi.service.IUserService;
import com.saodi.util.CurrentUser;
import com.saodi.vo.PageBean;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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
@Api(value = "影评与评分")
@RestController
@RequestMapping("/movie-comment")
public class MovieCommentController {

    private static final int MAX_CONTENT_LENGTH = 1000;

    @Autowired
    IMovieCommentService commentService;
    @Autowired
    IMovieService movieService;
    @Autowired
    IUserService userService;

    @ApiOperation("分页查询某部影片的影评，按时间倒序，附带作者信息")
    @GetMapping("/page/{movieId}")
    public ResponseObj page(@PathVariable("movieId") Integer movieId,
                            @RequestParam(value = "current", defaultValue = "1") Integer current,
                            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Page<MovieComment> pageInfo = PageHelper.startPage(current, size);
        List<MovieComment> list = commentService.list(new QueryWrapper<MovieComment>()
                .eq("movie_id", movieId)
                .orderByDesc("createtime"));
        attachAuthors(list);

        PageBean<MovieComment> bean = new PageBean<>();
        bean.setTotalRows((int) pageInfo.getTotal());
        bean.setTotalPages(pageInfo.getPages());
        bean.setData(list);
        return ResponseObj.SUCCESS(bean);
    }

    @ApiOperation("查询当前用户在该影片下写过的影评")
    @GetMapping("/mine/{movieId}")
    public ResponseObj mine(@PathVariable("movieId") Integer movieId, HttpServletRequest request) {
        Integer uid = CurrentUser.id(request);
        if (uid == null) {
            return ResponseObj.ERROR(501, "登录验证失败");
        }
        MovieComment comment = latestBy(movieId, uid);
        if (comment != null) {
            comment.setUser(userService.getById(uid));
            if (comment.getUser() != null) {
                comment.getUser().setPassword(null);
            }
        }
        return ResponseObj.SUCCESS(comment);
    }

    @ApiOperation("发表影评；同一用户对同一影片只保留最新一条")
    @PostMapping("/add")
    public ResponseObj add(@RequestBody MovieComment body, HttpServletRequest request) {
        Integer uid = CurrentUser.id(request);
        if (uid == null) {
            return ResponseObj.ERROR(501, "登录验证失败");
        }
        if (body.getMovieId() == null) {
            return ResponseObj.ERROR(502, "缺少影片id");
        }
        if (movieService.getById(body.getMovieId()) == null) {
            return ResponseObj.ERROR(503, "影片不存在");
        }
        String content = body.getContent() == null ? "" : body.getContent().trim();
        if (content.isEmpty()) {
            return ResponseObj.ERROR(504, "影评内容不能为空");
        }
        if (content.length() > MAX_CONTENT_LENGTH) {
            return ResponseObj.ERROR(505, "影评内容不能超过" + MAX_CONTENT_LENGTH + "字");
        }
        Double score = body.getScore();
        if (score == null || score < 1 || score > 10) {
            return ResponseObj.ERROR(506, "评分需在1~10之间");
        }

        MovieComment existing = latestBy(body.getMovieId(), uid);
        boolean saved;
        if (existing == null) {
            MovieComment comment = new MovieComment();
            comment.setMovieId(body.getMovieId());
            comment.setUserId(uid);
            comment.setContent(content);
            comment.setScore(score);
            comment.setCreatetime(LocalDateTime.now());
            saved = commentService.save(comment);
        } else {
            existing.setContent(content);
            existing.setScore(score);
            existing.setCreatetime(LocalDateTime.now());
            saved = commentService.updateById(existing);
        }
        if (!saved) {
            return ResponseObj.ERROR(507, "保存失败");
        }
        commentService.refreshMovieScore(body.getMovieId());
        return ResponseObj.SUCCESS("ok");
    }

    @ApiOperation("删除影评，只能删除自己写的")
    @DeleteMapping("/{id}")
    public ResponseObj delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        Integer uid = CurrentUser.id(request);
        if (uid == null) {
            return ResponseObj.ERROR(501, "登录验证失败");
        }
        MovieComment comment = commentService.getById(id);
        if (comment == null) {
            return ResponseObj.ERROR(502, "影评不存在");
        }
        if (!uid.equals(comment.getUserId())) {
            return ResponseObj.ERROR(510, "无权访问该资源");
        }
        commentService.removeById(id);
        commentService.refreshMovieScore(comment.getMovieId());
        return ResponseObj.SUCCESS("ok");
    }

    private MovieComment latestBy(Integer movieId, Integer uid) {
        return commentService.getOne(new QueryWrapper<MovieComment>()
                .eq("movie_id", movieId)
                .eq("user_id", uid)
                .orderByDesc("id")
                .last("limit 1"));
    }

    private void attachAuthors(List<MovieComment> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Set<Integer> ids = list.stream()
                .map(MovieComment::getUserId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (ids.isEmpty()) {
            return;
        }
        Map<Integer, User> authors = new HashMap<>();
        for (User user : userService.listByIds(ids)) {
            user.setPassword(null);
            authors.put(user.getId(), user);
        }
        for (MovieComment comment : list) {
            comment.setUser(authors.get(comment.getUserId()));
        }
    }
}
