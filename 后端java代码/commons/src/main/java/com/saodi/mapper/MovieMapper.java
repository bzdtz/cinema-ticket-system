package com.saodi.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.saodi.po.Movie;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.saodi.query.CinemaQuery;
import com.saodi.query.MovieQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface MovieMapper extends BaseMapper<Movie> {

    /**
     * 根据 想看人数=0 and 票房 返回数据
     * @return
     */
    List<Movie> getByWantAndBoxOffice(Integer size);

    /**
     * 根据 想看人数 返回数据
     * @return
     */
    List<Movie> getByWant(Integer size);

    /**
     * 获取三级分类下的电影模块
     * @return
     */
    List<Movie> getMovie(MovieQuery query);


    List<Movie>  getBanner(Integer queryId);


    List<String>  getTypeById(Integer  movieId);

    List<Movie> getByAccount(Integer account,  @Param("ew") Wrapper wrapper);
}
