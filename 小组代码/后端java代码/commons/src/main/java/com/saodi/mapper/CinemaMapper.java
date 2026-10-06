package com.saodi.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.Cinema;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.saodi.po.CinemaUser;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface CinemaMapper extends BaseMapper<Cinema> {

     Page<Cinema> getByPage(Page page, @Param("ew") Wrapper wrapper);
}
