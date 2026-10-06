package com.saodi.service.impl;

import com.saodi.po.OrderDetail;
import com.saodi.mapper.OrderDetailMapper;
import com.saodi.service.IOrderDetailService;
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
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

    @Autowired
    OrderDetailMapper mapper;

    @Override
    public List<OrderDetail> getSeat(Integer orderId) {
        List<OrderDetail> mapperSeat = mapper.getSeat(orderId);

        return mapperSeat;
    }
}
