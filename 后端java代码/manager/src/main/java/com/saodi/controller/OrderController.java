package com.saodi.controller;


import com.saodi.po.Order;
import com.saodi.query.OrderQuery;
import com.saodi.service.IOrderService;
import com.saodi.vo.PageBean;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    IOrderService service;

    @ApiOperation("显示所有的订单以及用户信息")
    @PostMapping("/page")
    public ResponseObj  getPage(@RequestBody OrderQuery query)
    {
        if (query==null)
        {
            return ResponseObj.ERROR(500,"erroe");
        }
        if (query.getCurrent()==null || query.getCurrent()==0)
        {
            query.setCurrent(1);
        }
        if (query.getSize()==null || query.getSize()==0)
        {
            query.setSize(8);
        }

        //分页校验成功进行
        query.setStartIndex((query.getCurrent()-1)*query.getSize());

        PageBean<Order>   pageBean=new PageBean<>();
        pageBean.setTotalRows(service.Count(query));
        List<Order> user = service.getOrderAndUser(query);
        pageBean.setData(user);
        return ResponseObj.SUCCESS("success",pageBean);

    }

    @ApiOperation("根据订单id  获取当前 的具体信息")
    @GetMapping("/getOrderDetail/{orderId}")
    public  ResponseObj   getDetail(@PathVariable("orderId") Integer orderId){

        Order orderDetail = service.getOrderDetail(orderId);
        return ResponseObj.SUCCESS("success",orderDetail);
    }




}
