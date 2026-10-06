package com.saodi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.saodi.po.Permission;
import com.saodi.mapper.PermissionMapper;

import com.saodi.query.PermissionQuery;
import com.saodi.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
    @Override
    public Page<Permission> getByPage(PermissionQuery query) {
        QueryWrapper<Permission> queryWrapper = Wrappers.query(new Permission())
                //StringUtils.hasText(query.getUsername())  StringUtils字符串工具类  判断变量是否有值
                //三个参数：该条件加不加 boolean值 ,  比对的字段名称(跟数据库一致) ,   比对的值
                .like(StringUtils.hasText(query.getName()),"name",query.getName());

        //存放分页条件
        Page pageQuery = new Page(query.getCurrent(),query.getSize());

        //自定义分页时  准备好分页条件Page  准备好搜索条件Wrapper
        Page<Permission> Page = baseMapper.selectPage(pageQuery, queryWrapper);
        return Page;
    }

    @Override
    public boolean Check(Integer account,String url) {
        Integer check = baseMapper.Check(account, url);
        if (check==0){
            return false;
        }
        return true;
    }


}
