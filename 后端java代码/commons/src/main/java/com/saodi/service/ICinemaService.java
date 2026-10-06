package com.saodi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.Cinema;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saodi.po.Permission;
import com.saodi.query.CinemaQuery;
import com.saodi.query.PermissionQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface ICinemaService extends IService<Cinema> {

    Page<Cinema> getByPage(CinemaQuery query);


    List<String>  getByBrand(String city);

    List<String>  getByCountry(String city);

    List<String>  getByType(String city);


}
