package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.po.Movie;
import com.saodi.po.Movietype;
import com.saodi.service.IMovieService;
import com.saodi.service.IMovietypeService;
import com.saodi.vo.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping("/movietype")
public class MovietypeController {

    @Autowired
    IMovietypeService service;

    @Autowired
    IMovieService  movieService;

    @GetMapping("/get")
    public ResponseObj getMovieType(){
        List<Movietype> list = service.list();

        return  ResponseObj.SUCCESS("查询成功",list);
    }

    @GetMapping("/region")
    public ResponseObj getMovieRegion(){
        List<Movie> list = movieService.list();
        List regionList=new ArrayList();
            for (Movie movie :list) {

                System.out.println(!regionList.contains(movie.getRegion()));
                if (!regionList.contains(movie.getRegion()))
                {
                    regionList.add(movie.getRegion());
                }
            }
            regionList.add(0,"全部");
            System.out.println(regionList);


        return  ResponseObj.SUCCESS("查询成功",regionList);
    }

    @GetMapping("/time")
    public ResponseObj getMovieTime(){
        List<Movie> list = movieService.list();
        List timeList=new ArrayList();
        for (Movie movie :list) {
            Date releaseYearStr = movie.getReleaseTime(); // 假设这个方法返回包含年份信息的字符串
            if (releaseYearStr != null) {
                LocalDate localDate = releaseYearStr.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int releaseYear = localDate.getYear();
                timeList.add(releaseYear);
            }

        }
        timeList.add(0,"全部");
        System.out.println(timeList);


        return  ResponseObj.SUCCESS("查询成功",timeList);
    }





}
