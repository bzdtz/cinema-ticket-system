package com.saodi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.Cinema;
import com.saodi.po.Showtimes;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saodi.query.DateCinemaQuery;
import com.saodi.query.ShowTimeQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface IShowtimesService extends IService<Showtimes> {


    Page<Showtimes> getByPage(ShowTimeQuery query);
    List<Showtimes>  getTimeAndHall(ShowTimeQuery query);
    Showtimes getById(Integer Id);


    List<Cinema> getCinemaByQuery(DateCinemaQuery query);

    Integer getByDateQueryCount(DateCinemaQuery query);
}
