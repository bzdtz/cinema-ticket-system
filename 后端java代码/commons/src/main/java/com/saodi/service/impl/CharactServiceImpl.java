package com.saodi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.Charact;
import com.saodi.mapper.CharactMapper;

import com.saodi.query.CharactQuery;
import com.saodi.service.ICharactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@Service
public class CharactServiceImpl extends ServiceImpl<CharactMapper, Charact> implements ICharactService {


    @Override
    public Page<Charact> getByPage(CharactQuery query) {
        QueryWrapper<Charact> queryWrapper = Wrappers.query(new Charact()).like(StringUtils.hasText(query.getName()), "name", query.getName());
        Page page = new Page(query.getCurrent(), query.getSize());
        Page<Charact> Page= baseMapper.selectPage(page, queryWrapper);
        return page;
    }
}
