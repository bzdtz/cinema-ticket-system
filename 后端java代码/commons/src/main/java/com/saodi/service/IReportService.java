package com.saodi.service;

import com.saodi.query.ReportQuery;

import java.util.Map;

/**
 * <p>
 *  报表服务
 * </p>
 *
 * @author saodi
 */
public interface IReportService {

    Map<String, Object> overview(ReportQuery query);

    Map<String, Object> trend(ReportQuery query);

    Map<String, Object> ranking(ReportQuery query);

    Map<String, Object> occupancy(ReportQuery query);
}
