package com.saodi.service;

import com.saodi.po.MovieComment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface IMovieCommentService extends IService<MovieComment> {

    /**
     * 用该影片当前全部影评的均分回写 movie.score。
     * 影评被删空时不回写，避免把导入的原始评分覆盖成 0。
     */
    void refreshMovieScore(Integer movieId);
}
