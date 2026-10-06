package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.po.MovieTypeMapping;
import com.saodi.po.Movietype;
import com.saodi.service.IMovieTypeMappingService;
import com.saodi.vo.MovieTypeMappingVo;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/movie-type-mapping")
public class MovieTypeMappingController {

    @Autowired
    IMovieTypeMappingService service;

    @ApiOperation("根据电影id 类型id  将该电影id 移除该类")
    @GetMapping("/del/{mid}/{tid}")
    public ResponseObj  delMovieTypeMapping(@PathVariable("mid")Integer mid,@PathVariable("tid")Integer  tid){

        QueryWrapper<MovieTypeMapping>  queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("movie_id",mid).eq("type_id",tid);
        boolean b = service.remove(queryWrapper);
        return  b?ResponseObj.SUCCESS("删除成功"):ResponseObj.ERROR(500,"删除失败");
    }
//    @PostMapping("/del/{tid}")
//    public ResponseObj  delBatchMovieTypeMapping(@PathVariable("tid")Integer  tid,Integer[]  mids){
//
//        QueryWrapper<MovieTypeMapping>  queryWrapper=new QueryWrapper<>();
//
//        queryWrapper.eq("type_id",tid);
//        for (Integer integer :mids) {
//            queryWrapper.eq("")
//        }
//
//        return  b?ResponseObj.SUCCESS("删除成功"):ResponseObj.ERROR(500,"删除失败");
//    }


    @ApiOperation("添加电影id 与类型id 映射")
    @PostMapping("add")
    public  ResponseObj   addMovieIdTypeId(@RequestBody MovieTypeMapping vo)
    {
        boolean b = service.save(vo);
        return  b?ResponseObj.SUCCESS("成功"):ResponseObj.ERROR(500,"error");
    }

    @ApiOperation("删除电影 应将对应的类型id都删了")
    @GetMapping("/del/{id}")
    public  ResponseObj   delMovieTypeId(@PathVariable("id") Integer movieId){

        QueryWrapper<MovieTypeMapping> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("movie_id", movieId);
        boolean b = service.remove(queryWrapper);
        return b?ResponseObj.SUCCESS("success"):ResponseObj.ERROR(500,"error");
    }


//    @ApiOperation("根据电影id 获取电影类型")
}
