package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.po.Movie;
import com.saodi.po.MovieImg;
import com.saodi.service.IMovieImgService;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
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
 *
 * @author saodi
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/movie-img")
public class MovieImgController {

    @Autowired
    IMovieImgService service;


    /**
     * 根据前端传来的电影id  去查询表中 对应的电影所具有的图片
     * @param movieId
     * @return
     */

    @ApiOperation("通过movieId返回当前电影的所有图集")
    @GetMapping("/imgs/{movieId}")
    public ResponseObj getMovieImg(@PathVariable("movieId") Integer movieId)
    {
        QueryWrapper<MovieImg>  imgQueryWrapper=new QueryWrapper<>();
        imgQueryWrapper.eq("movie_id" ,movieId);
        List<MovieImg> movieImgs = service.list(imgQueryWrapper);
        return  ResponseObj.SUCCESS("查询成功",movieImgs);
    }
}
