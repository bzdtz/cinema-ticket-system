package com.saodi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.Charact;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saodi.query.CharactQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface ICharactService extends IService<Charact> {

    public Page<Charact> getByPage(CharactQuery query);
}
