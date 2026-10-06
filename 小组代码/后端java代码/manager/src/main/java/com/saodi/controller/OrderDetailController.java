package com.saodi.controller;


import com.saodi.po.OrderDetail;
import com.saodi.service.IOrderDetailService;
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
 *
 * @author saodi
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/order-detail")
public class OrderDetailController {

    @Autowired
    IOrderDetailService service;

    @ApiOperation("根据 订单查看当前的座位")
    @GetMapping("/getSeat/{orderId}")
    public ResponseObj  getSeat(@PathVariable("orderId") Integer orderId)
    {
        List<OrderDetail> seats = service.getSeat(orderId);
        return  ResponseObj.SUCCESS("success",seats);
    }
}
