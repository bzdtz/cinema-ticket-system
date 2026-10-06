package com.saodi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.Cinema;
import com.saodi.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saodi.query.CinemaQuery;
import com.saodi.query.UserQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface IUserService extends IService<User> {
    Page<User> getByPage(UserQuery query);
    List<Integer> getHistory(Integer id);
}
