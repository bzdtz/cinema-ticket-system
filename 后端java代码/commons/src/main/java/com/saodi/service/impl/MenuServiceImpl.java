package com.saodi.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.mapper.RoleMenuMapper;
import com.saodi.po.Menu;
import com.saodi.mapper.MenuMapper;

import com.saodi.po.RoleMenu;
import com.saodi.query.MenuQuery;
import com.saodi.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    /**
     * 通过传入menuQuery 返回树结构
     *
     * @param query
     * @return
     */

    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Override
    public List<Menu> getTree(MenuQuery query) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like(StringUtils.hasText(query.getName()), "name", query.getName());

        List<Menu> all = baseMapper.selectList(queryWrapper);

        return tree(all);

    }

    @Override
    public List<Menu> getRoleMenu(Integer roleId) {
        System.out.println(roleId);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id", roleId);
        queryWrapper.select("menu_id");
        List<Integer> list = roleMenuMapper.selectObjs(queryWrapper);
        System.out.println(list);
        List<Menu> menus = baseMapper.selectBatchIds(list);


        return tree(menus);
    }

    /**
     * 将list构建成树
     *
     * @param all
     * @return
     */
    public List<Menu> tree(List<Menu> all) {
        //构建了一个一级菜单的集合
        List<Menu> first = new ArrayList<>();

        Map<Integer, Menu> map = new HashMap();
        //遍历所有菜单对象 找出一级菜单
        for (Menu menu : all) {
            if (menu.getParentId() == null) {
                map.put(menu.getId(), menu);//为了后面可以快速查找
                first.add(menu);//将一级菜单的对象加入一级菜单的集合
            }
        }
        //循环所有菜单  找出不是一级菜单的
        for (Menu menu : all) {
            if (menu.getParentId() != null) {
                //先根据父菜单的id从map中取出父菜单对象
                Menu parent = map.get(menu.getParentId());
                //将自己放入父菜单的子菜单集合中
                parent.getMenuList().add(menu);
            }
        }
        return first;
    }

    @Override
    public List<Menu> getByLevel(Integer integer) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("level", integer);
        List<Menu> list = baseMapper.selectList(queryWrapper);
        return list;
    }




}
