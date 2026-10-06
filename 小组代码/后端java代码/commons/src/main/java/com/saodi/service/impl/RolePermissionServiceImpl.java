package com.saodi.service.impl;

import com.saodi.po.RolePermission;
import com.saodi.mapper.RolePermissionMapper;
import com.saodi.service.IRolePermissionService;
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
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

    @Autowired
    RolePermissionMapper mapper;

    @Override
    public List<RolePermission> getPermission(String account) {

        List<RolePermission> rolePermissionList = mapper.getByRoleId(account);
        return rolePermissionList;
    }
}
