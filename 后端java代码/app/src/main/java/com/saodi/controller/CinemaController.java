package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.saodi.po.Cinema;
import com.saodi.po.Movie;
import com.saodi.query.CinemaQuery;
import com.saodi.service.ICinemaService;
import com.saodi.vo.PageBean;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@Api(value = "影院操作")
@RestController
@RequestMapping("/cinema")
public class CinemaController {

    @Autowired
    ICinemaService service;

    /**
     *  返回的数据对应前端需要的 影院   有一个条件就是当前用户选择的定位位置
     */
    @PostMapping("/list")
    public  ResponseObj getCinema(String city){

        QueryWrapper<Cinema> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("city",city);
        List<Cinema> cinemaList = service.list(queryWrapper);
        List<Cinema> brand=new ArrayList<>();
        for (Cinema cinema :cinemaList) {
            
        }


        return  ResponseObj.SUCCESS("查询成功",cinemaList);

    }

    @ApiOperation("根据前端传来的地址返回三条数据  影院品牌  行政区 影厅类型")
    @GetMapping("/get/{city}")
    public  ResponseObj getQuery(@PathVariable("city")String city)
    {
        if (city==null){
            city="";
        }
        List<String> brands = service.getByBrand(city);
        List<String> countrys = service.getByCountry(city);
        List<String> types = service.getByType(city);

        List<List<String>> datas=new ArrayList<>();
        datas.add(brands);
        datas.add(countrys);
        datas.add(types);
        return ResponseObj.SUCCESS("获取成功",datas);
    }


    @ApiOperation("条件参数 ：brand  country   type 可无   但是 city必须有   ")
    @PostMapping("/getCinema")
    public  ResponseObj  getByQuery(@RequestBody  CinemaQuery query)
    {

        System.out.println("收到前端发来的请求");
        System.out.println(query.getCurrent());


        QueryWrapper<Cinema>  queryWrapper=new QueryWrapper<>();
        //分页
        if (query.getCurrent()==null){
            query.setCurrent(1);
        }
        if (query.getSize()==null)
        {
            query.setSize(8);
        }

        Page<Cinema> page = PageHelper.startPage(query.getCurrent(), query.getSize());
        if (query.getCity()==null)
        {
            query.setCity("");

        }

        queryWrapper.like("city",query.getCity());
        if (query.getBrand()==null || "all".equals(query.getBrand()))
        {
            //前端没有传入品牌限制那么，就查询所有的影院
            query.setBrand("");
        }
        queryWrapper.like("brand",query.getBrand());

        if (query.getCountry()==null || "all".equals(query.getCountry())){
            //前端没有传 行政区限制 那么就查询所有的影院
            query.setCountry("");
        }
        queryWrapper.like("country",query.getCountry());

        if (query.getType()==null || "all".equals(query.getType()))
        {
            //前端没有影厅限制那么就 查询所有
            query.setType("");
        }
        queryWrapper.like("type",query.getType());



        List<Cinema> list = service.list(queryWrapper);

        PageBean<Cinema>  pageBean=new PageBean<>();
        pageBean.setTotalRows((int)page.getTotal());
        pageBean.setTotalPages((int)page.getPages());
        pageBean.setData(page.getResult());

        return ResponseObj.SUCCESS("查询成功",pageBean);
    }



    @ApiOperation("表名：cinemaId  通过cinemaId来获取当前影院的具体信息")
    @GetMapping("/getByid/{id}")
    public ResponseObj  getById(@PathVariable("id") Integer id){
        QueryWrapper<Cinema>  queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        Cinema cinema = service.getOne(queryWrapper);
        return  ResponseObj.SUCCESS("查询成功",cinema);
    }





}
