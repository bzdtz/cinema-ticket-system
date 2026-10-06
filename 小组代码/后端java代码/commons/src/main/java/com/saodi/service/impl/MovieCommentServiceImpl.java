package com.saodi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.po.Movie;
import com.saodi.po.MovieComment;
import com.saodi.mapper.MovieCommentMapper;
import com.saodi.mapper.MovieMapper;
import com.saodi.service.IMovieCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@Service
public class MovieCommentServiceImpl extends ServiceImpl<MovieCommentMapper, MovieComment> implements IMovieCommentService {

    @Autowired
    MovieMapper movieMapper;

    @Override
    public void refreshMovieScore(Integer movieId) {
        List<MovieComment> all = list(new QueryWrapper<MovieComment>()
                .select("score")
                .eq("movie_id", movieId));
        if (all.isEmpty()) {
            return;
        }
        Movie movie = movieMapper.selectById(movieId);
        if (movie == null) {
            return;
        }
        double avg = all.stream()
                .filter(c -> c.getScore() != null)
                .mapToDouble(MovieComment::getScore)
                .average()
                .orElse(0D);
        movie.setScore(Math.round(avg * 10) / 10.0);
        movieMapper.updateById(movie);
    }
}
