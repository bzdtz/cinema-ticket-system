package com.saodi.controller;

import com.saodi.query.ReportQuery;
import com.saodi.service.IReportService;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  后台报表
 * </p>
 *
 * 挂在 /order/report 而不是 /report，是为了复用 permission 表里已有的 /order 授权
 * （PermissionMapper 按 'url/%' 前缀匹配），两个角色都能看，不必新增权限行。
 *
 * @author saodi
 */
@RestController
@RequestMapping("/order/report")
public class ReportController {

    @Autowired
    private IReportService service;

    @ApiOperation("营收概况与数据体检")
    @PostMapping("/overview")
    public ResponseObj overview(@RequestBody(required = false) ReportQuery query) {
        return ResponseObj.SUCCESS(service.overview(orDefault(query)));
    }

    @ApiOperation("按天营收趋势")
    @PostMapping("/trend")
    public ResponseObj trend(@RequestBody(required = false) ReportQuery query) {
        return ResponseObj.SUCCESS(service.trend(orDefault(query)));
    }

    @ApiOperation("影片与影院票房排行")
    @PostMapping("/ranking")
    public ResponseObj ranking(@RequestBody(required = false) ReportQuery query) {
        return ResponseObj.SUCCESS(service.ranking(orDefault(query)));
    }

    @ApiOperation("场次上座率")
    @PostMapping("/occupancy")
    public ResponseObj occupancy(@RequestBody(required = false) ReportQuery query) {
        return ResponseObj.SUCCESS(service.occupancy(orDefault(query)));
    }

    private static ReportQuery orDefault(ReportQuery query) {
        return query == null ? new ReportQuery() : query;
    }
}
