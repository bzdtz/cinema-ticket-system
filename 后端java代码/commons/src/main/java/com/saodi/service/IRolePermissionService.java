package com.saodi.service;

import com.saodi.po.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface IRolePermissionService extends IService<RolePermission> {


    List<RolePermission>  getPermission(String account);

}
