package com.saodi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.Movietype;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saodi.query.MovietypeQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public interface IMovietypeService extends IService<Movietype> {

    Page<Movietype> getByPage(MovietypeQuery movietypeQuery);


}
