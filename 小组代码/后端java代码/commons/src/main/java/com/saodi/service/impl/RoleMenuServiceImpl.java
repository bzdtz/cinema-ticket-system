package com.saodi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.po.RoleMenu;
import com.saodi.mapper.RoleMenuMapper;
import com.saodi.service.IRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {
    @Override
    public boolean updateMenuRole(Integer role, List<RoleMenu> list) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id",role);
        int delete = baseMapper.delete(queryWrapper);
        if (delete>0){
            //调用service层的批量添加
            return saveBatch(list);
        }
        return false;


    }
}
