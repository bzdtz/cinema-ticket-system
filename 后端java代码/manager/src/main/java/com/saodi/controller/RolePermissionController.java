package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.po.RolePermission;
import com.saodi.service.IRolePermissionService;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/role-permission")
public class RolePermissionController {

    @Autowired
    IRolePermissionService service;

    @ApiOperation("通过当前用户的account来搜索 具有的权限")
    @GetMapping("/get/{id}")
    public ResponseObj  getPermission(@PathVariable("id")String  account)
    {
        //涉及到多表联查了
        List<RolePermission> permission = service.getPermission(account);

        return ResponseObj.SUCCESS("获取成功",permission);

    }


    @PostMapping("/changeRolePermissions/{roleId}")
    public  ResponseObj changeRolePermissions(@RequestBody List<RolePermission> list,@PathVariable("roleId" ) Integer id){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id",id);
        boolean remove = service.remove(queryWrapper);
        if (remove){
            boolean b = service.saveBatch(list);
            return  b?ResponseObj.SUCCESS():ResponseObj.ERROR();
        }
        return ResponseObj.ERROR();
    }

}
