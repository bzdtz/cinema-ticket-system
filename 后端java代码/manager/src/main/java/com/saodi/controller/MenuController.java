package com.saodi.controller;


import com.saodi.po.Menu;
import com.saodi.po.RoleMenu;
import com.saodi.query.MenuQuery;
import com.saodi.service.IMenuService;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
//
    @Autowired
    IMenuService service;

//    @ApiImplicitParam(name = "accessToken", required = false,paramType = "header",dataType = "String")
    @PostMapping("/tree")
    public ResponseObj tree(@RequestBody MenuQuery query) {
        List<Menu> list = service.getTree(query);
        return ResponseObj.SUCCESS(list);
    }

    @PostMapping("/roleMenu/{roleId}")
    public ResponseObj getRoleMenu(@PathVariable("roleId") Integer roleId){
        List<Menu> roleMenu = service.getRoleMenu(roleId);
        return ResponseObj.SUCCESS(roleMenu);
    }

    @PostMapping("/saveorupdate")
    public  ResponseObj saveOrUpdate(@RequestBody Menu menu){
        boolean b = service.saveOrUpdate(menu);
        return  b?ResponseObj.SUCCESS():ResponseObj.ERROR();
    }
    @PostMapping("/level/{level}")
    public  ResponseObj getByLevel(@PathVariable ("level") Integer level){
        List<Menu> byLevel = service.getByLevel(level);
        return byLevel!=null?ResponseObj.SUCCESS(byLevel):ResponseObj.ERROR();
    }

    @PostMapping("/saveorupdatebarch")
    public  ResponseObj saveOrUpdatebarch(@RequestBody List<Menu> menu){
        boolean b = service.saveOrUpdateBatch(menu);
        return  b?ResponseObj.SUCCESS():ResponseObj.ERROR();
    }
}
