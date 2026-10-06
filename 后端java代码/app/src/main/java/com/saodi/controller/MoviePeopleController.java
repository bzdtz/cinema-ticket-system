package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.po.Movie;
import com.saodi.po.MoviePeople;
import com.saodi.service.IMoviePeopleService;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 * 该接口主要是 查询 非演员信息
 * @author saodi
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/movie-people")
public class MoviePeopleController {

    @Autowired
    IMoviePeopleService service;

    @ApiOperation("根据当前的movieId返回演职人员")
    @GetMapping("/get/{movieId}")
    public ResponseObj getPeopleById(@PathVariable("movieId") Integer movieId)
    {
        QueryWrapper<MoviePeople> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("movie_id",movieId);
        List<MoviePeople> peopleList = service.list(queryWrapper);
        return  ResponseObj.SUCCESS("查询成功",peopleList);
    }


}
