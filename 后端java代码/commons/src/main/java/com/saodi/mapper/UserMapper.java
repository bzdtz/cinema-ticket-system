package com.saodi.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface UserMapper extends BaseMapper<User> {
    List<Integer> getHistory(@Param("ew") Wrapper wrapper);
//    Page<User> getByPage(Page page, @Param("ew") Wrapper wrapper);
}
