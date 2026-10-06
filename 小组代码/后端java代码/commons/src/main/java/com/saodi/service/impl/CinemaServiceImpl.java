package com.saodi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.Cinema;
import com.saodi.mapper.CinemaMapper;
import com.saodi.po.Permission;
import com.saodi.query.CinemaQuery;
import com.saodi.query.PermissionQuery;
import com.saodi.service.ICinemaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@Service
public class CinemaServiceImpl extends ServiceImpl<CinemaMapper, Cinema> implements ICinemaService {
    @Autowired
    CinemaMapper cinemaMapper;

    @Override
    public Page<Cinema> getByPage(CinemaQuery query) {
        QueryWrapper<Cinema> queryWrapper = Wrappers.query(new Cinema())
                //StringUtils.hasText(query.getUsername())  StringUtils字符串工具类  判断变量是否有值
                //三个参数：该条件加不加 boolean值 ,  比对的字段名称(跟数据库一致) ,   比对的值
                .like(StringUtils.hasText(query.getName()), "u.name", query.getName())
                .like(StringUtils.hasText(query.getProvince()), "province", query.getProvince())
                .like(StringUtils.hasText(query.getCity()), "city", query.getCity())
                .like(StringUtils.hasText(query.getCountry()), "country", query.getCountry())
                .like(StringUtils.hasText(query.getSpecifiedAddress()), "specified_address", query.getSpecifiedAddress())
                .like(StringUtils.hasText(query.getTag()), "tag", query.getTag())
                .like(StringUtils.hasText(query.getType()), "type", query.getType())
                .between(query.getPriceMax() == null ? false : query.getPriceMin() != null ? true : false, "price", query.getPriceMin(), query.getPriceMax())
                ;
        ;


        //存放分页条件
        Page pageQuery = new Page(query.getCurrent(), query.getSize());

        //自定义分页时  准备好分页条件Page  准备好搜索条件Wrapper
        Page<Cinema> Page = cinemaMapper.getByPage(pageQuery, queryWrapper);
        return Page;
    }

    @Override
    public List<String> getByBrand(String city) {
        QueryWrapper<Cinema> queryWrapper = new QueryWrapper<>();
        //确定要查询的字段
        queryWrapper.select("brand");
        //确定相等的条件
        queryWrapper.eq("city", city);
        queryWrapper.groupBy("brand");

        List<Cinema> cinemaList = cinemaMapper.selectList(queryWrapper);
        List<String> brands = new ArrayList<>();
        for (Cinema cinema : cinemaList) {
            if (cinema == null) {
                continue;
            }
            if (cinema.getBrand() == null) {
                continue;
            }
            brands.add(cinema.getBrand());
        }

        return brands;
    }

    @Override
    public List<String> getByCountry(String city) {
        QueryWrapper<Cinema> queryWrapper = new QueryWrapper<>();
        //确定要查询的字段
        queryWrapper.select("country");
        //确定相等的条件
        queryWrapper.eq("city", city);
        queryWrapper.groupBy("country");

        List<Cinema> cinemaList = cinemaMapper.selectList(queryWrapper);
        List<String> countrys = new ArrayList<>();
        for (Cinema cinema : cinemaList) {
            if (cinema == null) {
                continue;
            }
            if (cinema.getCountry() == null) {
                continue;
            }
            countrys.add(cinema.getCountry());
        }

        return countrys;

    }

    @Override
    public List<String> getByType(String city) {
        QueryWrapper<Cinema> queryWrapper = new QueryWrapper<>();
        //确定要查询的字段
        queryWrapper.select("type");
        //确定相等的条件
        queryWrapper.eq("city", city);
        queryWrapper.groupBy("type");

        List<Cinema> cinemaList = cinemaMapper.selectList(queryWrapper);
        System.out.println("这个是cinemaList的数据");
        System.out.println(cinemaList);
        List<String> types = new ArrayList<>();
        for (Cinema cinema : cinemaList) {
            if (cinema == null) {
                continue;
            }
            if (cinema.getType() == null) {
                continue;
            }

            types.add(cinema.getType());

        }
        List<String> type = new ArrayList<>();
        System.out.println("这个是所有的types");
        System.out.println(types);
        for (String s : types) {
            if (s == null || "".equals(s)) {
                continue;
            }
            if (s.contains("|")) {
                String[] split = s.split("\\|");
                for (String s1 : split) {
                    if (!s1.trim().isEmpty()) { // 避免添加空字符串
                        type.add(s1.trim());
                    }

                }
            } else {
                type.add(s.trim()); // 如果不包含 | ，直接添加到结果集
            }
        }
        System.out.println("解析结束");
        System.out.println(type);
        return type;
    }
}
