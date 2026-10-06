package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.CinemaUser;
import com.saodi.po.Permission;
import com.saodi.query.PermissionQuery;
import com.saodi.service.IPermissionService;
import com.saodi.service.IRolePermissionService;
import com.saodi.vo.ResponseObj;
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
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    IPermissionService permissionService;
/*
权限不设置增加和删除操作
修改中可设置是否启用
*/
    @Autowired
    IRolePermissionService  rolePermissionService;

    /**
     * 更新操作
     * @param permission
     * @return
     */
    @PostMapping("/update")
    public ResponseObj update(@RequestBody Permission permission){
        boolean b = permissionService.saveOrUpdate(permission);
        return  b?ResponseObj.SUCCESS():ResponseObj.ERROR();
    }

    /**
     * 分页查询搜索
     * @param query
     * @return
     */
    @PostMapping("/page")
    public ResponseObj page(@RequestBody PermissionQuery query){

        Page<Permission> page = permissionService.getByPage(query);
        return ResponseObj.SUCCESS(page);
    }

    @PostMapping("/batchupdate")
    public ResponseObj batchUpdate(@RequestBody List<Permission> list){
        if (list==null){
            return ResponseObj.ERROR();
        }
        list.forEach(System.out::println);
        boolean b = permissionService.updateBatchById(list);
        return b?ResponseObj.SUCCESS():ResponseObj.ERROR();
    }

    @PostMapping("/getById/{id}")
    public  ResponseObj getById(@PathVariable("id")Integer id){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id",id);
        List list = rolePermissionService.list(queryWrapper);
        return  list!=null?ResponseObj.SUCCESS(list):ResponseObj.ERROR();
    }
}
