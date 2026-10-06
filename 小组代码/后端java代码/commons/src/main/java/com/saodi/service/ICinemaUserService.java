package com.saodi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.CinemaUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saodi.query.CinemaUserQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface ICinemaUserService extends IService<CinemaUser> {

    Page<CinemaUser> getByPage(CinemaUserQuery cinemaUserQuery);

}
