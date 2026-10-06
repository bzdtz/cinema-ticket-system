package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.po.Order;
import com.saodi.po.OrderDetail;
import com.saodi.service.IOrderDetailService;
import com.saodi.service.IOrderService;
import com.saodi.util.CurrentUser;
import com.saodi.vo.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 前端控制器
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

    @Autowired
    IOrderService orderService;

    @PostMapping("/addSeat")
    public ResponseObj add(@RequestBody List<OrderDetail> orderDetails, HttpServletRequest request)
    {
        Integer currentUserId = CurrentUser.id(request);
        if (currentUserId == null) {
            return ResponseObj.ERROR(501, "登录验证失败");
        }
        if (orderDetails == null || orderDetails.isEmpty()) {
            return ResponseObj.ERROR(508, "座位信息不能为空");
        }

        Set<Integer> orderIds = new HashSet<>();
        for (OrderDetail orderDetail : orderDetails) {
            orderIds.add(orderDetail.getOrderId());
        }

        // 每条座位都必须挂在当前用户自己的订单下
        long owned = orderService.count(new QueryWrapper<Order>()
                .eq("user_id", currentUserId)
                .in("order_id", orderIds));
        if (owned != orderIds.size()) {
            return ResponseObj.ERROR(510, "无权操作该订单");
        }

        boolean b = service.saveBatch(orderDetails);
        return b ? ResponseObj.SUCCESS() : ResponseObj.ERROR();
    }
}
