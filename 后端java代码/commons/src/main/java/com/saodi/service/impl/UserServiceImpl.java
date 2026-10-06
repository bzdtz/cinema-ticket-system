package com.saodi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.mapper.MovieMapper;
import com.saodi.po.User;
import com.saodi.mapper.UserMapper;
import com.saodi.query.UserQuery;
import com.saodi.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Page<User> getByPage(UserQuery userQuery) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.hasText(userQuery.getUsername()), "user_name", userQuery.getUsername())
                .like(StringUtils.hasText(userQuery.getEmail()), "email", userQuery.getEmail())
                .eq(StringUtils.hasText(userQuery.getPhone()), "phone", userQuery.getPhone());
        Page pageQuery = new Page(userQuery.getCurrent(), userQuery.getSize());
//        Page<User> byPage = userMapper.getByPage(pageQuery, wrapper);
        Page<User> selectPage = userMapper.selectPage(pageQuery, wrapper);
        return selectPage;
    }

    @Override
    public List<Integer> getHistory(Integer id) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(id!=null,"user_id",id);
        wrapper.eq("order.status","已支付");
        List<Integer> history = userMapper.getHistory(wrapper);

        return history;
    }

}
