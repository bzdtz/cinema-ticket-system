package com.saodi.service;

import com.saodi.po.ActorMovie;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface IActorMovieService extends IService<ActorMovie> {

    /**
     * 根据电影Id 获取当前电影的演员 饰演信息
     */
    List<ActorMovie> getByMovieId(Integer movieId);
}
