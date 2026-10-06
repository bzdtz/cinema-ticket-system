package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.saodi.po.Order;
import com.saodi.service.IOrderService;
import com.saodi.service.IShowtimesService;
import com.saodi.util.CurrentUser;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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
    @Autowired
    IShowtimesService showtimesService;
    @PostMapping("/add")
    public ResponseObj add(@RequestBody Order order, HttpServletRequest request){

        Integer currentUserId = CurrentUser.id(request);
        if (currentUserId == null){
            return ResponseObj.ERROR(501,"登录验证失败");
        }
        // 订单归属由服务端决定，不接受客户端传入的 userId
        order.setUserId(currentUserId);

        Date currentTime = new Date();
        // 使用SimpleDateFormat将日期时间格式化为 "yyyy-MM-dd HH:mm:ss" 形式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateFormat.format(currentTime);

        Date formattedDateTimeObject = null;
        try {
            formattedDateTimeObject = dateFormat.parse(formattedDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }




        order.setLastConfirmTime(formattedDateTimeObject);
        System.out.println(order.getSeat());
        boolean b = service.saveOrUpdate(order);
        Integer orderId = order.getOrderId();
        UpdateWrapper uw = new UpdateWrapper();
        uw.set("seat",order.getSeat());
        uw.eq("id",order.getShowtimesId());
        boolean update = showtimesService.update(uw);
        System.out.println(orderId);
        return update?ResponseObj.SUCCESS(orderId):ResponseObj.ERROR();
    }

    @ApiOperation("根据订单id进行修改订单状态")
    @GetMapping("/update/{id}")
    public  ResponseObj  updateOrderStatus(@PathVariable("id") Integer orderId, HttpServletRequest request)
    {
        Integer currentUserId = CurrentUser.id(request);
        if (currentUserId == null){
            return ResponseObj.ERROR(501,"登录验证失败");
        }

        QueryWrapper<Order>  queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("order_id",orderId);
        Order order = service.getOne(queryWrapper);
        if (order == null){
            return ResponseObj.ERROR(502,"订单不存在");
        }
        if (!currentUserId.equals(order.getUserId())){
            return ResponseObj.ERROR(510,"无权操作该订单");
        }
        // 已支付则不再重复支付，避免同一订单被反复改状态
        if ("已支付".equals(order.getStatus())){
            return ResponseObj.SUCCESS("success");
        }

        // 生成6位数字UUID
        String paymentId = generateNumericUUID(6);
        order.setPayId(Integer.parseInt(paymentId));

        // 在接收到请求时，创建一个当前时间
        Date currentTime = new Date();
        // 使用SimpleDateFormat将日期时间格式化为 "yyyy-MM-dd HH:mm:ss" 形式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateFormat.format(currentTime);

        try {
            Date formattedDateTimeObject =dateFormat.parse(formattedDateTime) ;
            order.setPayTime(formattedDateTimeObject);

        } catch (Exception e) {
            e.printStackTrace();
        }


        order.setStatus("已支付");

        boolean b = service.updateById(order);

        return b?ResponseObj.SUCCESS("success"):ResponseObj.ERROR(500,"error");
    }

    // 生成指定长度的数字UUID
    private String generateNumericUUID(int length) {
        Random random = new Random();
        StringBuilder uuid = new StringBuilder();

        for (int i = 0; i < length; i++) {
            // 生成0到9的数字并追加到uuid
            uuid.append(random.nextInt(10));
        }

        return uuid.toString();
    }

}
