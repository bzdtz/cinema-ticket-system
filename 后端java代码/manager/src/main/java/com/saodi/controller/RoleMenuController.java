package com.saodi.controller;


import com.saodi.po.RoleMenu;
import com.saodi.service.IRoleMenuService;
import com.saodi.vo.ResponseObj;
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
@RequestMapping("/role-menu")
public class RoleMenuController {
    @Autowired
    IRoleMenuService service;
    @PostMapping("/updateMenuRole/{role}")
    public ResponseObj updateMenuRole(@PathVariable("role") Integer role, @RequestBody List<RoleMenu> roleMenu){
        boolean b = service.updateMenuRole(role, roleMenu);
        return b?ResponseObj.SUCCESS():ResponseObj.ERROR();
    }
}
