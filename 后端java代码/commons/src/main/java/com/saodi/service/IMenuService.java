package com.saodi.service;

import com.saodi.po.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saodi.po.RoleMenu;
import com.saodi.query.MenuQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface IMenuService extends IService<Menu> {
    List<Menu> getTree(MenuQuery menuQuery);

    List<Menu> getRoleMenu(Integer roleId);
    List<Menu> getByLevel(Integer integer);


}
