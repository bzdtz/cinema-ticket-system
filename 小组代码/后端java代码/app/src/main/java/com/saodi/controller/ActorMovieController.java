package com.saodi.controller;



import com.saodi.po.ActorMovie;
import com.saodi.service.IActorMovieService;
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
 * 该接口主要是用于 获取 演员的相关信息
 * @author saodi
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/actor-movie")
public class ActorMovieController {

    @Autowired
    IActorMovieService service;

    /**
     * 根据电影id 去查询关于该电影 所需要的演员 【并不是查询非演员的方法】
     * @param movieId
     * @return
     */
    @ApiOperation("根据movieId 去查询对应的主演名称以及角色")
    @GetMapping("/detail/{id}")
    public ResponseObj getById(@PathVariable("id")Integer movieId){

        List<ActorMovie> movieList = service.getByMovieId(movieId);
        return  ResponseObj.SUCCESS("查询成功",movieList);
    }

}
