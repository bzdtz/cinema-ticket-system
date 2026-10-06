package com.saodi.mapper;

import com.saodi.po.Permission;
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
public interface PermissionMapper extends BaseMapper<Permission> {


   Integer Check(@Param("account") Integer account,@Param("url") String url);

}
