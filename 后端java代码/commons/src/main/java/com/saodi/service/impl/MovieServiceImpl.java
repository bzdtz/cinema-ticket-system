package com.saodi.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.saodi.po.Movie;
import com.saodi.mapper.MovieMapper;
import com.saodi.query.CinemaQuery;
import com.saodi.query.MovieQuery;
import com.saodi.service.IMovieService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saodi.vo.PageBean;
import com.saodi.vo.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie> implements IMovieService {

    @Autowired
     MovieMapper movieMapper;

    @Override
    public List<Movie> getByWantAndBoxOffice(Integer size) {
//        QueryWrapper<Movie> wrapper = new QueryWrapper<>();
//         wrapper.eq("want_number", 0).orderBy(true, false, "box_office").last("limit 5");


        List<Movie> movieList = movieMapper.getByWantAndBoxOffice(size);

        System.out.println("service________________+++++++");
        System.out.println(movieList);
        return movieList;
    }

    @Override
    public List<Movie> getByWant(Integer size) {
//        QueryWrapper<Movie> wrapper=new QueryWrapper<>();
//        wrapper.eq(false,"want_number",0).orderBy(true,false,"want_number").last("limit 4");
        List<Movie> movies = movieMapper.getByWant(size);
        return movies;
    }

    @Override
    public List<Movie> getMovie(MovieQuery movieQuery) {
        System.out.println("=================++++++++++++++++++++++++=======");
        System.out.println(movieQuery);
        if (movieQuery.getTypeId()==null)
        {
            //没有涉及到联查的就这样
            QueryWrapper<Movie> queryWrapper=new QueryWrapper<>();
            if (movieQuery.getYear()!=null)
            {
                queryWrapper.like("release_time",movieQuery.getYear());
            }
            if (movieQuery.getRegion()!=null)
            {
                //有区域限制，但是不需要联查
                queryWrapper.like("region",movieQuery.getRegion());

            }
            if (movieQuery.getBoxOffice()!=null)
            {
                //按照票房排序
                queryWrapper.orderBy(false,false,"box_office");
                movieQuery.setScore(null);
                movieQuery.setReleaseTime(null);
            }
            if (movieQuery.getScore()!=null)
            {
                queryWrapper.orderBy(true,false,"score");
                movieQuery.setBoxOffice(null);
                movieQuery.setReleaseTime(null);
            }

            if (movieQuery.getReleaseTime()!=null)
            {
                //有查询时间不需要联查就自己查了
                queryWrapper.orderBy(true,false,"release_time");
                movieQuery.setScore(null);
                movieQuery.setBoxOffice(null);
            }

            List<Movie> movies = movieMapper.selectList(queryWrapper);
            return movies;
        }
        //涉及到联查了
        List<Movie> movies = movieMapper.getMovie(movieQuery);
        return movies;
    }

    @Override
    public PageBean<Movie> getPage(MovieQuery movieQuery) {
         //                   当前页                   一页记录数

        Page<Movie> page = PageHelper.startPage(movieQuery.getCurrent(), movieQuery.getSize());

        PageBean<Movie> pageBean=new PageBean<>();
        if (movieQuery==null)
        {

            return pageBean;
        }
        System.out.println(movieQuery);
        System.out.println(movieQuery.getTypeId());
        if (movieQuery.getTypeId()==1)
        {
            //没有涉及到联查的就这样
            QueryWrapper<Movie> queryWrapper=new QueryWrapper<>();
            if (movieQuery.getYear()!=null)
            {
                queryWrapper.like("release_time",movieQuery.getYear());
            }
            if (movieQuery.getRegion()!=null)
            {
                //有区域限制，但是不需要联查
                queryWrapper.like("region",movieQuery.getRegion());

            }
            if (movieQuery.getBoxOffice()!=null)
            {
                //按照票房排序
                queryWrapper.orderBy(true,false,"box_office");
                movieQuery.setScore(null);
                movieQuery.setReleaseTime(null);
            }
            if (movieQuery.getScore()!=null)
            {
                queryWrapper.orderBy(true,false,"score");
                movieQuery.setBoxOffice(null);
                movieQuery.setReleaseTime(null);
            }

            if (movieQuery.getReleaseTime()!=null)
            {
                //有查询时间不需要联查就自己查了

                queryWrapper.orderBy(true,false,"release_time");
                movieQuery.setScore(null);
                movieQuery.setBoxOffice(null);
            }
            //来自管理员界面的请求
            if (movieQuery.getName()!=null)
            {
                //通过name进行模糊搜索
                queryWrapper.like("name",movieQuery.getName());

            }



            queryWrapper.orderByDesc("id");

            List<Movie> movies = movieMapper.selectList(queryWrapper);
            pageBean.setTotalRows((int)page.getTotal());
            pageBean.setTotalPages(page.getPages());

            pageBean.setData(movies);
            return pageBean;
        }
        //涉及到联查了
        List<Movie> movies = movieMapper.getMovie(movieQuery);
        pageBean.setTotalRows((int)page.getTotal());
        pageBean.setTotalPages(page.getPages());
        pageBean.setData(page.getResult());
        return pageBean;
    }

    @Override
    public List<Movie> getBanner(Integer queryId) {

        List<Movie> movieList = movieMapper.getBanner(queryId);
        return movieList;
    }

    @Override
    public List<String> getTypeById(Integer movieId) {

        List<String> types = movieMapper.getTypeById(movieId);

        return types;
    }

    @Override
    public List<Movie> getByAccount(Integer Account) {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<Movie>(new Movie()).eq(Account != null, "account", Account);
        List<Movie> list = movieMapper.getByAccount(Account, queryWrapper);
        return list;
    }


}
