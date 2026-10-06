package com.saodi.service.impl;

import com.saodi.po.Order;
import com.saodi.mapper.OrderMapper;
import com.saodi.query.OrderQuery;
import com.saodi.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    OrderMapper mapper;
    @Override
    public List<Order> getOrder(Integer id) {
        List<Order> order = mapper.getOrder(id);
        return order;
    }


    @Override
    public List<Order> getOrderAndUser(OrderQuery query) {

        List<Order> userAndOrder = mapper.getUserAndOrder(query);

        return userAndOrder;
    }

    @Override
    public Integer Count(OrderQuery query) {
        Integer count = mapper.getCount(query);
        return count;
    }

    @Override
    public Order getOrderDetail(Integer orderId) {
        Order orderDetail = mapper.getOrderDetail(orderId);
        return orderDetail;
    }
}
