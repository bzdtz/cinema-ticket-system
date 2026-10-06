package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.saodi.po.Order;
import com.saodi.po.Ticket;
import com.saodi.service.IOrderService;
import com.saodi.service.ITicketService;
import com.saodi.util.CurrentUser;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    ITicketService service;

    @Autowired
    IOrderService orderService;

    @ApiOperation("支付成功 返回票据")
    @GetMapping("/getTic/{id}")
    public ResponseObj getTicket(@PathVariable("id") Integer orderId, HttpServletRequest request)
    {
        Integer currentUserId = CurrentUser.id(request);
        if (currentUserId == null){
            return ResponseObj.ERROR(501,"登录验证失败");
        }

        Order order = orderService.getOne(new QueryWrapper<Order>().eq("order_id", orderId));
        if (order == null){
            return ResponseObj.ERROR(502,"订单不存在");
        }
        if (!currentUserId.equals(order.getUserId())){
            return ResponseObj.ERROR(510,"无权查看该票据");
        }
        if (!"已支付".equals(order.getStatus())){
            return ResponseObj.ERROR(509,"订单未支付");
        }

        Ticket ticket = service.getOne(new QueryWrapper<Ticket>().eq("order_id", orderId));
        if (ticket == null){
            return ResponseObj.ERROR(503,"该订单没有票据");
        }

        String ticketCode = ticket.getTicketCode();
        if (!StringUtils.hasText(ticketCode)){
            // 占位实现：真正的取票码规则应该随核销/退票功能一起设计
            ticketCode = String.format("%09d", ThreadLocalRandom.current().nextInt(1000000000));
            UpdateWrapper<Ticket> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", ticket.getId()).set("ticket_code", ticketCode);
            service.update(updateWrapper);
        }

        return ResponseObj.SUCCESS("success", ticketCode);
    }

}
