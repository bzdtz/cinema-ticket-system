package com.saodi.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.Movietype;
import com.saodi.query.MovietypeQuery;
import com.saodi.service.IMovietypeService;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/movietype")
@Api(tags = "电影类型接口", description = "")
public class MovietypeController {

    @Autowired
    IMovietypeService movietypeService;

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public ResponseObj page(@RequestBody MovietypeQuery query){
        Page<Movietype> byPage = movietypeService.getByPage(query);
        return  byPage!=null?ResponseObj.SUCCESS(byPage):ResponseObj.ERROR();
    }


    @ApiOperation("修改")
    @PostMapping("/update")
    public  ResponseObj update(@RequestBody Movietype movietype) {
        boolean b = movietypeService.saveOrUpdate(movietype);
        return b?ResponseObj.SUCCESS("200","修改成功"):ResponseObj.ERROR();
    }

}
