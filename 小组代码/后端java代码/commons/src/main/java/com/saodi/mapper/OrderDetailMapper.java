package com.saodi.mapper;

import com.saodi.po.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    List<OrderDetail>  getSeat(Integer orderId);

}
