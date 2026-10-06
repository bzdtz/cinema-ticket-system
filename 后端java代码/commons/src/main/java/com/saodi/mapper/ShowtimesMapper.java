package com.saodi.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.Cinema;
import com.saodi.po.Showtimes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.saodi.query.DateCinemaQuery;
import com.saodi.query.ShowTimeQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface ShowtimesMapper extends BaseMapper<Showtimes> {

    List<String>  getDate(Integer cid,Integer mid);

    Page<Showtimes> getByPage(Page page, @Param("ew") Wrapper wrapper,@Param("status") Integer status);

    List<Showtimes>  getTimeAndHall(ShowTimeQuery query);
    Showtimes getById(Integer id, @Param("ew") Wrapper wrapper);


    List<Cinema>  getByDateQuery(DateCinemaQuery query);
    Integer getByDateQueryCount(DateCinemaQuery query);
}
