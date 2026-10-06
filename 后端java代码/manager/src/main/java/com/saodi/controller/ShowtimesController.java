package com.saodi.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.Hall;
import com.saodi.po.Showtimes;
import com.saodi.query.ShowTimeQuery;
import com.saodi.service.IHallService;
import com.saodi.service.IShowtimesService;
import com.saodi.vo.ResponseObj;
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
@RequestMapping("/showtimes")
public class ShowtimesController {

    @Autowired
    IShowtimesService service;
    @Autowired
    IHallService hallService;
    @PostMapping("/page")
    public ResponseObj getByPage(@RequestBody ShowTimeQuery query){
        Page<Showtimes> byPage = service.getByPage(query);
        return byPage!=null?ResponseObj.SUCCESS(byPage):ResponseObj.ERROR();
    }


    @PostMapping("/getById/{id}")
    public ResponseObj getById(@PathVariable("id") Integer id){
        Showtimes byId = service.getById(id);
        return byId!=null?ResponseObj.SUCCESS(byId):ResponseObj.ERROR(502,"场次不存在");
    }

    @PostMapping("/saveOrUpdate")
    public  ResponseObj saveOrUpdate(@RequestBody Showtimes showtimes){
        Hall hall = hallService.getById(showtimes.getHallId());


        showtimes.setSeat(hall.getHallSize());
        System.out.println(showtimes);
        boolean b = service.saveOrUpdate(showtimes);

        return  b?ResponseObj.SUCCESS():ResponseObj.ERROR();

    }

    @PostMapping("/del/{id}")
    public  ResponseObj del (@PathVariable("id") Integer id){
        boolean b = service.removeById(id);
        return b?ResponseObj.SUCCESS():ResponseObj.ERROR();
    }
}
