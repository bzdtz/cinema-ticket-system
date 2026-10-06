package com.saodi.service.impl;

import com.saodi.po.ActorMovie;
import com.saodi.mapper.ActorMovieMapper;
import com.saodi.service.IActorMovieService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
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
public class ActorMovieServiceImpl extends ServiceImpl<ActorMovieMapper, ActorMovie> implements IActorMovieService {
    @Autowired
    ActorMovieMapper  movieMapper;

    @Override
    public List<ActorMovie> getByMovieId(Integer movieId) {

        List<ActorMovie> actorMovies = movieMapper.getByMovieId(movieId);

        return actorMovies;
    }
}
