package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.po.MovieCinemaMapping;
import com.saodi.po.MovieTypeMapping;
import com.saodi.service.IMovieTypeMappingService;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
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
@RequestMapping("/movie-cinema-mapping")
public class MovieCinemaMappingController {

    @Autowired
    IMovieTypeMappingService service;


    @ApiOperation("根据movieId 先删除当前所有的类型再 加入当前传入的类型")
    @PostMapping("/update/{movieId}")
    public ResponseObj  updateMovieTypeMapping(@PathVariable("movieId")Integer movieId,@RequestBody Collection<Integer> typeIds)
    {

        QueryWrapper<MovieTypeMapping> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("movie_id",movieId);
        boolean remove = service.remove(queryWrapper);
        if (remove)
        {
            //删除成功
            List<MovieTypeMapping>  list=new ArrayList<>();
            for (Integer typeId :typeIds) {
                MovieTypeMapping  movieTypeMapping=new MovieTypeMapping();
                movieTypeMapping.setMovieId(movieId+"");
                movieTypeMapping.setTypeId(typeId);
                list.add(movieTypeMapping);
            }//循环结束
            boolean b = service.saveBatch(list);
            return b?ResponseObj.SUCCESS("更新数据类型成功"):ResponseObj.ERROR(500,"失败");


        }

        return ResponseObj.ERROR(500,"更新操作失败");
    }

}
