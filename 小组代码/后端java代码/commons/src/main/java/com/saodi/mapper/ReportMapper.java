package com.saodi.mapper;

import com.saodi.po.Showtimes;
import com.saodi.query.ReportQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  报表聚合查询
 * </p>
 *
 * 口径说明：营收只认 `order`.total_price，且一律从 `order` 出发做 LEFT JOIN。
 * 订单页用的是 INNER JOIN，场次被删掉的订单在那边看不见，直接沿用会让报表少报收入。
 *
 * @author saodi
 */
public interface ReportMapper {

    Map<String, Object> overview(ReportQuery query);

    List<Map<String, Object>> trend(ReportQuery query);

    List<Map<String, Object>> topMovies(ReportQuery query);

    List<Map<String, Object>> topCinemas(ReportQuery query);

    Map<String, Object> detailCoverage();

    List<Showtimes> seatSnapshots(ReportQuery query);
}
