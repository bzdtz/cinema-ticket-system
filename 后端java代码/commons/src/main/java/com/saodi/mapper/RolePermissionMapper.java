package com.saodi.mapper;

import com.saodi.po.Permission;
import com.saodi.po.RolePermission;
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
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    //通过roleId 来查询都有哪些权限 permission
    List<RolePermission> getByRoleId(String roleId);
}
