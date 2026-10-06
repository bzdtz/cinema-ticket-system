package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.saodi.po.Cinema;
import com.saodi.po.Showtimes;
import com.saodi.query.DateCinemaQuery;
import com.saodi.query.ShowTimeQuery;
import com.saodi.service.IShowtimesService;
import com.saodi.vo.PageBean;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/showtimes")
public class ShowtimesController {

    @Autowired
    IShowtimesService showtimesService;

    @ApiOperation("根据影院id，电影id展示当前电影即将上映的日期")
    @GetMapping("/date/{cid}/{mid}")
    public ResponseObj getDate(@PathVariable("cid") Integer cid, @PathVariable("mid") Integer mid) {

        LocalDate currentDate = LocalDate.now();

        if (cid==-1)
        {
            cid=null;
        }
        QueryWrapper<Showtimes>  queryWrapper=new QueryWrapper<>();
        queryWrapper.select("showdate");
        //                       字符串方法用以判断当前是否为null
        queryWrapper.eq(cid!=null ,"cinema_id",cid);
        queryWrapper.eq("movie_id",mid);
        // 添加大于当前日期的条件
        queryWrapper.ge("showdate", currentDate);


        queryWrapper.groupBy("showdate");
        List<Showtimes> showtimesList = showtimesService.list(queryWrapper);

        return  ResponseObj.SUCCESS("获取成功",showtimesList);


    }


    @ApiOperation("表：排片表，影厅表   获取排片信息 三个参数 指定影院，指定电影  指定日期eg：2023-12-29")
    @PostMapping("/showtime")
    public  ResponseObj  getShowTime(@RequestBody ShowTimeQuery query)
    {
        System.out.println("++++++++++++++++++++++++++");
        System.out.println(query);
        if (query==null)
        {
            return ResponseObj.ERROR(500,"失败");
        }
        if (query.getCinemaId()==null || query.getMovieId()==null || query.getShowTime()==null)
        {
            return ResponseObj.ERROR(500,"失败");
        }
        List<Showtimes> showtimes = showtimesService.getTimeAndHall(query);
        return ResponseObj.SUCCESS("成功",showtimes);


    }

    @PostMapping("/getById/{id}")
    public ResponseObj getById(@PathVariable("id") Integer id){
        Showtimes byId = showtimesService.getById(id);
        return byId!=null?ResponseObj.SUCCESS(byId):ResponseObj.ERROR();
    }

    @PostMapping("/getCinema")
    public  ResponseObj  getCinema(@RequestBody DateCinemaQuery query)
    {


        System.out.println("展示一下，当我我所获取到的查询条件");
        System.out.println(query);
        if (query.getCurrent()==null)
        {
            query.setCurrent(1);
        }
        if (query.getSize()==null)
        {
            query.setSize(8);
        }
        query.setStartIndex((query.getCurrent()-1)* query.getSize());


        System.out.println("这个是前端发来的查询条件");
        if (query.getBrand()==null ||  "".equals(query.getBrand()) || "all".equals(query.getBrand()))
        {
            query.setBrand(null);
        }
        if (query.getCity()==null || "".equals(query.getCity()))
        {
            query.setCity(null);
        }
        if (query.getType()==null || "".equals(query.getType()))
        {
            query.setType(null);
        }
        if (query.getUserDate()==null || "".equals(query.getUserDate()))
        {
            query.setUserDate(null);
        }
        PageBean<Cinema> pageBean=new PageBean<>();
        Integer count = showtimesService.getByDateQueryCount(query);
        pageBean.setTotalRows(count);
        System.out.println("有多少的数据");
        System.out.println(count);

        List<Cinema> cinemas = showtimesService.getCinemaByQuery(query);
        pageBean.setData(cinemas);
        return  ResponseObj.SUCCESS("查询成功",pageBean);

    }


}
