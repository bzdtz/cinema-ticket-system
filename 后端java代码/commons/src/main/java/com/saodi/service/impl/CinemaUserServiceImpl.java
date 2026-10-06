package com.saodi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.CinemaUser;
import com.saodi.mapper.CinemaUserMapper;
import com.saodi.query.CinemaUserQuery;
import com.saodi.service.ICinemaUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@Service
public class CinemaUserServiceImpl extends ServiceImpl<CinemaUserMapper, CinemaUser> implements ICinemaUserService {


    @Override
    public Page<CinemaUser> getByPage(CinemaUserQuery cinemaUserQuery) {
        QueryWrapper<CinemaUser> queryWrapper = Wrappers.query(new CinemaUser())
                //StringUtils.hasText(query.getUsername())  StringUtils字符串工具类  判断变量是否有值
                //三个参数：该条件加不加 boolean值 ,  比对的字段名称(跟数据库一致) ,   比对的值
                .like(StringUtils.hasText(cinemaUserQuery.getUsername()),"u.name",cinemaUserQuery.getUsername())
                .like(StringUtils.hasText(cinemaUserQuery.getEmail()),"email",cinemaUserQuery.getEmail())
                .eq(StringUtils.hasText(cinemaUserQuery.getPhone()),"phone",cinemaUserQuery.getPhone())
                .orderByDesc("id");

        //存放分页条件
        Page pageQuery = new Page(cinemaUserQuery.getCurrent(),cinemaUserQuery.getSize());

        //自定义分页时  准备好分页条件Page  准备好搜索条件Wrapper
        Page<CinemaUser> departmentIPage = baseMapper.selectJoinRole(pageQuery, queryWrapper);
        return departmentIPage;
    }


}
