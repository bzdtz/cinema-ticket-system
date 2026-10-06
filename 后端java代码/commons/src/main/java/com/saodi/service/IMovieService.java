package com.saodi.service;

import com.saodi.po.Movie;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saodi.query.CinemaQuery;
import com.saodi.query.MovieQuery;
import com.saodi.vo.PageBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface IMovieService extends IService<Movie> {


    /**
     * 根据 想看人数=0  and  票房数量 去返回热映电影
     * @return
     */
    List<Movie>  getByWantAndBoxOffice(Integer size);


    /**
     * 根据 想看的人数的多少 选出4部
     * @return
     */
    List<Movie> getByWant(Integer size);

    /**
     * 条件查询  三个条件：类型   区域     年份      热门/时间/评价
     *             电影的类型  上映国家  上映那一年  票房  具体上映时间   评分
     * @return
     */
    List<Movie> getMovie(MovieQuery movieQuery);

    PageBean<Movie>  getPage(MovieQuery movieQuery);


    List<Movie>  getBanner(Integer queryId);


    List<String>   getTypeById(Integer movieId);


    List<Movie> getByAccount(Integer Account);
}
