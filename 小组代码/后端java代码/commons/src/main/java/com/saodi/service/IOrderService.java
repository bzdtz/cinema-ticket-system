package com.saodi.service;

import com.saodi.po.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;
import com.saodi.query.OrderQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface IOrderService extends IService<Order> {

   public List<Order> getOrder(Integer id);

    List<Order>     getOrderAndUser(OrderQuery query);

    Integer Count(OrderQuery query);


    Order  getOrderDetail(Integer orderId);

}
