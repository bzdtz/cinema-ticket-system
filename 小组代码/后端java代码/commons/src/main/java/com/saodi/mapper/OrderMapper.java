package com.saodi.mapper;

import com.saodi.po.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.saodi.query.OrderQuery;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface OrderMapper extends BaseMapper<Order> {
    List<Order> getOrder(Integer id);


    List<Order>   getUserAndOrder(OrderQuery query);

    Integer  getCount(OrderQuery query);

   Order getOrderDetail(Integer orderId);
}
