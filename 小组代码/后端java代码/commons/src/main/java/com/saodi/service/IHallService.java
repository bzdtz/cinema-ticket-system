package com.saodi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.Hall;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saodi.query.HallQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface IHallService extends IService<Hall> {

   boolean init(Integer id);

   Page getByPage(HallQuery query);

}
