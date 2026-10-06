package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.po.Cinema;
import com.saodi.po.Movie;
import com.saodi.query.MovieQuery;
import com.saodi.service.IMovieService;
import com.saodi.vo.PageBean;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@Api(value = "对电影进行操作")
@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    IMovieService service;

    /**
     * 根据 票房以及上映时间 来返回数据
     * 目标数据：票房最高 且 已经上映【根据想看人数的数据是否为0】
     * @return
     */
    @ApiOperation("正在热映板块  参数 ：size 用以限制返回数据  不传默认为8")
    @GetMapping("/index/hot/{id}")
    public ResponseObj  getMovieByTimeAndBoxOffice(@PathVariable("id") Integer size)
    {

        if (size==null)
        {
            size=8;
        }
        List<Movie> movieList = service.getByWantAndBoxOffice(size);

        System.out.println("++++++++++++++++++++++++++++++++++++++");
        System.out.println(movieList);
        return ResponseObj.SUCCESS("查询成功",movieList);
    }

    /**
     * 查询 电影根据想看人数判断
     * @return
     */
    @ApiOperation("即将上映板块    参数 ：size 用以限制返回数据  不传默认为8")
    @GetMapping("/index/want/{id}")
    public ResponseObj getMovieByWant(@PathVariable("id") Integer size)
    {
        if (size==null)
        {
            size=8;
        }

        List<Movie> movieList = service.getByWant(size);

        System.out.println("++++++++++++++++++++++++++++++++++++++");
        System.out.println(movieList);
        return ResponseObj.SUCCESS("查询成功",movieList);
    }

    /**
     * 三级条件查询  类型 + 区域 + 上映年份 + 一个排序条件
     * @param query
     * @return
     */
    @ApiOperation("目前不会调用这个方法")
    @PostMapping("/get")
    public ResponseObj getMovie(@RequestBody MovieQuery query)
    {

        //前端选择 全部类型   后端该字段设置为null
        if (query.getTypeId().equals(1))
        {
            query.setTypeId(null);
        }
        //前端选择 全部区域   后端该字段设置为null
        if (query.getRegion().equals("全部"))
        {
            query.setRegion(null);
        }

        List<Movie> movieList = service.getMovie(query);

        System.out.println("++++++++++++++++++++++++++++++++++++++");
        System.out.println(movieList);
        return ResponseObj.SUCCESS("查询成功",movieList);
    }

    /**
     * 根据电影的Id 去查询电影信息并返回
     * @param movieId
     * @return
     */
    @ApiOperation("根据movieId查询当前电影的详细信息")
    @GetMapping("/{movieId}")
    public ResponseObj  getMovieById(@PathVariable("movieId") Integer movieId)
    {

        Movie movie = service.getById(movieId);
        return ResponseObj.SUCCESS("查询成功",movie);
    }


    @ApiOperation(
            "条件参数：typeId：32  --》全部类型   1 --》喜剧   2---》xxx   该参数不传则null表全部类型 " +
            "条件参数：region：“全部”--》全部区域  ”中国大陆“ ---》中国大陆      该参数不传则null表全部区域" +
            "条件参数：year  ： 不传则为null 表全部年份 " +
            "排序参数：三者中两者为null  其余不为null 则不为null 者排序条件生效  三者都为null则无排序条件")
    @PostMapping("/page")
    public ResponseObj getPage(@RequestBody MovieQuery query)
    {

        System.out.println("controller query");
        System.out.println(query);

        if (query.getTypeId()==null )
        {
            query.setTypeId(32);
        }
        if (query.getRegion()==null || "全部".equals(query.getRegion()))
        {
            query.setRegion(null);
        }

        System.out.println("=============================================");
        System.out.println(query);
        PageBean<Movie> pageBean = service.getPage(query);

        System.out.println("++++++++++++++++++++++++++++++++++++++");
        System.out.println(pageBean);
        return ResponseObj.SUCCESS("查询成功",pageBean);
    }


    @ApiOperation("点击想看，将当前的电影中的want 字段加1")
    @GetMapping("/wantAdd/{id}")
    public  ResponseObj addWantNumber(@PathVariable("id") Integer movieId)
    {
        //通过id获取对象
        Movie movie= service.getById(movieId);
        Integer wantNumber=movie.getWantNumber();
        //进行加1
        movie.setWantNumber(wantNumber+1);
        QueryWrapper<Movie> wrapper=new QueryWrapper<>();
        wrapper.eq("id",movieId);
        boolean update = service.update(wrapper);
        return  update?ResponseObj.SUCCESS("想看人数+1成功"):ResponseObj.ERROR();
    }


    @ApiOperation("获取top10")
    @GetMapping("/getTop")
    public ResponseObj  getTop()
    {
        QueryWrapper<Movie> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderBy(true,false,"box_office");
        queryWrapper.last("limit 10");
        List<Movie> movies = service.list(queryWrapper);
        return  ResponseObj.SUCCESS("获取成功",movies);

    }

    @ApiOperation("表：排片表，影院表   参数：影院的id   返回给前端banner海报以及电影id")
    @GetMapping("/getBanner/{id}")
    public  ResponseObj  getBannerById(@PathVariable("id") Integer id)
    {

        List<Movie> movieList = service.getBanner(id);

        return ResponseObj.SUCCESS("查询成功",movieList);
    }


    @ApiOperation("表：movie，movieType  movie_type_mapping   根据movieId 返回对应的类型")
    @GetMapping("/type/{id}")
    public  ResponseObj  getTypeById(@PathVariable("id") Integer movieId)
    {
        List<String> types = service.getTypeById(movieId);
        return ResponseObj.SUCCESS("查询成功",types);
    }




}
