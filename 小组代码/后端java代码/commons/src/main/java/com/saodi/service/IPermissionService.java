package com.saodi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.CinemaUser;
import com.saodi.po.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saodi.query.CinemaUserQuery;
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
public interface IPermissionService extends IService<Permission> {
    public Page<Permission> getByPage(PermissionQuery query);
    public boolean Check(Integer account,String url);
}
