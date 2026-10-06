package com.saodi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.Cinema;
import com.saodi.po.Showtimes;
import com.saodi.mapper.ShowtimesMapper;
import com.saodi.query.DateCinemaQuery;
import com.saodi.query.ShowTimeQuery;
import com.saodi.service.IShowtimesService;
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
public class ShowtimesServiceImpl extends ServiceImpl<ShowtimesMapper, Showtimes> implements IShowtimesService {



    @Autowired
    ShowtimesMapper mapper;

    @Override
    public Page<Showtimes> getByPage(ShowTimeQuery query) {

        QueryWrapper<Showtimes> between = new QueryWrapper<Showtimes>(new Showtimes())
                .like(StringUtils.hasText(query.getCinemaName()), "cinema_name", query.getCinemaName())
                .like(StringUtils.hasText(query.getMovieName()), "movie_name", query.getMovieName())
                .eq(StringUtils.hasText(query.getShowTime()), "showdate", query.getShowTime())
                .between(StringUtils.hasText(query.getStart()) && StringUtils.hasText(query.getEnd()), "showdate", query.getStart(), query.getEnd())
                .between(query.getStartPrice() != null && query.getEndPrice() != null, "sale", query.getStartPrice(), query.getEndPrice());

        Page<Showtimes> page = new Page<>(query.getCurrent(), query.getSize());
        Page<Showtimes> byPage = mapper.getByPage(page, between,query.getStatus());

        return byPage;
    }

    @Override
    public List<Showtimes> getTimeAndHall(ShowTimeQuery query)
    {

        List<Showtimes> showtimesList = mapper.getTimeAndHall(query);

        return showtimesList;
    }

    @Override
    public Showtimes getById(Integer Id) {

        QueryWrapper<Showtimes> queryWrapper = new QueryWrapper<Showtimes>(new Showtimes()).eq(Id != null, "showtimes.id", Id);
        Showtimes byId = mapper.getById(Id, queryWrapper);
        return byId;
    }


    @Override
    public List<Cinema> getCinemaByQuery(DateCinemaQuery query) {

        List<Cinema> cinemas = mapper.getByDateQuery(query);


        return cinemas;
    }

    @Override
    public Integer getByDateQueryCount(DateCinemaQuery query) {
        Integer count = mapper.getByDateQueryCount(query);

        System.out.println(count);
        return count;
    }
}
