package com.saodi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.Hall;
import com.saodi.mapper.HallMapper;
import com.saodi.query.HallQuery;
import com.saodi.service.IHallService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saodi.util.HallUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@Service
public class HallServiceImpl extends ServiceImpl<HallMapper, Hall> implements IHallService {

    @Autowired
    HallMapper mapper;

    @Override
    public boolean init(Integer id) {
        Integer x = 10;
        Integer y = 10;
        int[][] array = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                array[i][j] = 0;
            }
        }
        String s = HallUtil.twoDimensionalArrayToString(array);
        System.out.println(s);
        UpdateWrapper uw = new UpdateWrapper();
        uw.set("hall_size", s);
        uw.eq(id != null ? true : false, "id", id);
        int update = mapper.update(null, uw);
        return true;
    }

    @Override
    public Page getByPage(HallQuery query) {
        QueryWrapper<Hall> hallQueryWrapper = new QueryWrapper<>(new Hall())
                .like(StringUtils.hasText(query.getName()), "hall_name", query.getName())
                .like(StringUtils.hasText(query.getCinemaName()), "name", query.getCinemaName()).orderByDesc("id");

        Page page = new Page(query.getCurrent(), query.getSize());
        Page<Hall> byPage = mapper.getByPage(page, hallQueryWrapper);
        return byPage;
    }


}

