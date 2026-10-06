package com.saodi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.Cinema;
import com.saodi.po.Movietype;
import com.saodi.mapper.MovietypeMapper;
import com.saodi.query.CinemaQuery;
import com.saodi.query.MovietypeQuery;
import com.saodi.service.IMovietypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MovietypeServiceImpl extends ServiceImpl<MovietypeMapper, Movietype> implements IMovietypeService {

    @Autowired
    MovietypeMapper  movietypeMapper;


//    @Override
//    public Page<Cinema> getByPage(CinemaQuery query) {
//        QueryWrapper<Cinema> queryWrapper = Wrappers.query(new Cinema())
//                //StringUtils.hasText(query.getUsername())  StringUtils字符串工具类  判断变量是否有值
//                //三个参数：该条件加不加 boolean值 ,  比对的字段名称(跟数据库一致) ,   比对的值
//                .like(StringUtils.hasText(query.getName()), "u.name", query.getName())
//                .like(StringUtils.hasText(query.getProvince()), "province", query.getProvince())
//                .like(StringUtils.hasText(query.getCity()), "city", query.getCity())
//                .like(StringUtils.hasText(query.getCountry()), "country", query.getCountry())
//                .like(StringUtils.hasText(query.getSpecifiedAddress()), "specified_address", query.getSpecifiedAddress())
//                .like(StringUtils.hasText(query.getTag()), "tag", query.getTag())
//                .like(StringUtils.hasText(query.getType()), "type", query.getType())
//                .between(query.getPriceMax() == null ? false : query.getPriceMin() != null ? true : false, "price", query.getPriceMin(), query.getPriceMax())
//                .orderByDesc("price");
//        ;
//
//
//        //存放分页条件
//        Page pageQuery = new Page(query.getCurrent(), query.getSize());
//
//        //自定义分页时  准备好分页条件Page  准备好搜索条件Wrapper
//        Page<Cinema> Page = cinemaMapper.getByPage(pageQuery, queryWrapper);
//        return Page;
//    }
    @Override
    public Page<Movietype> getByPage(MovietypeQuery movietypeQuery) {
        QueryWrapper<Movietype> queryWrapper = Wrappers.query(new Movietype()).like(StringUtils.hasText(movietypeQuery.getName()),"typename",movietypeQuery.getName());
        Page page = new Page(movietypeQuery.getCurrent(), movietypeQuery.getSize());
        Page<Movietype> byPage = movietypeMapper.getByPage(page, queryWrapper);

        return byPage;
    }
}
