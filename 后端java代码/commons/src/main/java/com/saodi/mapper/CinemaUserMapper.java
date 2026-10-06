package com.saodi.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.CinemaUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface CinemaUserMapper extends BaseMapper<CinemaUser> {

    Page<CinemaUser> selectJoinRole(Page page, @Param("ew") Wrapper wrapper);
}
