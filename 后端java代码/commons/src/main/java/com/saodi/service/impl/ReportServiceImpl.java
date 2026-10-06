package com.saodi.service.impl;

import com.saodi.mapper.ReportMapper;
import com.saodi.po.Showtimes;
import com.saodi.query.ReportQuery;
import com.saodi.service.IReportService;
import com.saodi.util.SeatMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.saodi.util.SeatMatrix.DAMAGED;
import static com.saodi.util.SeatMatrix.FREE;
import static com.saodi.util.SeatMatrix.NO_SEAT;
import static com.saodi.util.SeatMatrix.SOLD;
import static com.saodi.util.SeatMatrix.UNKNOWN;

/**
 * <p>
 *  报表服务实现
 * </p>
 *
 * @author saodi
 */
@Service
public class ReportServiceImpl implements IReportService {

    // 上面 static import 的五个名字是 count() 返回数组的下标（SeatMatrix.FREE..UNKNOWN），不是座位取值

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public Map<String, Object> overview(ReportQuery query) {
        Map<String, Object> scoped = reportMapper.overview(query);
        // 前端默认区间要用数据本身的跨度，所以全局跨度单独取一次，不受当前区间影响
        Map<String, Object> global = reportMapper.overview(new ReportQuery());

        Map<String, Object> paid = new LinkedHashMap<>();
        paid.put("orders", scoped.get("paidOrders"));
        paid.put("amount", scoped.get("paidAmount"));
        paid.put("avg", divide(scoped.get("paidAmount"), scoped.get("paidOrders")));

        Map<String, Object> unpaid = new LinkedHashMap<>();
        unpaid.put("orders", scoped.get("unpaidOrders"));
        unpaid.put("amount", scoped.get("unpaidAmount"));

        Map<String, Object> health = new LinkedHashMap<>();
        health.put("undatedOrders", scoped.get("undatedOrders"));
        health.put("paidUndatedOrders", scoped.get("paidUndatedOrders"));
        health.put("otherStatusOrders", scoped.get("otherStatusOrders"));
        health.put("lostShowtimeOrders", scoped.get("lostShowtimeOrders"));
        health.put("lostShowtimeAmount", scoped.get("lostShowtimeAmount"));
        health.put("lostUserOrders", scoped.get("lostUserOrders"));
        health.putAll(reportMapper.detailCoverage());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalOrders", scoped.get("totalOrders"));
        result.put("paid", paid);
        result.put("unpaid", unpaid);
        result.put("health", health);
        result.put("span", span(global));
        return result;
    }

    @Override
    public Map<String, Object> trend(ReportQuery query) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("rows", reportMapper.trend(query));
        return result;
    }

    @Override
    public Map<String, Object> ranking(ReportQuery query) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("movies", reportMapper.topMovies(query));
        result.put("cinemas", reportMapper.topCinemas(query));
        return result;
    }

    @Override
    public Map<String, Object> occupancy(ReportQuery query) {
        List<Showtimes> snapshots = reportMapper.seatSnapshots(query);
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat minuteFormat = new SimpleDateFormat("HH:mm");

        List<Map<String, Object>> rows = new ArrayList<>();
        Group total = new Group();
        Map<String, Group> byMovie = new LinkedHashMap<>();
        Map<String, Group> byCinema = new LinkedHashMap<>();

        for (Showtimes showtimes : snapshots) {
            int[] counts = SeatMatrix.count(showtimes.getSeat());
            // 分母只算真实座位：过道(-1)和未知取值都不进去，否则空厅会被过道拉低
            int seats = counts[FREE] + counts[SOLD] + counts[DAMAGED];

            String movieName = name(showtimes.getMovie() == null ? null : showtimes.getMovie().getName(), "（影片缺失）");
            String cinemaName = name(showtimes.getCinema() == null ? null : showtimes.getCinema().getName(), "（影院缺失）");

            total.screenings++;
            total.seats += seats;
            total.sold += counts[SOLD];
            total.damaged += counts[DAMAGED];
            total.unknown += counts[UNKNOWN];
            accumulate(byMovie, movieName, seats, counts[SOLD], counts[DAMAGED]);
            accumulate(byCinema, cinemaName, seats, counts[SOLD], counts[DAMAGED]);

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", showtimes.getId());
            row.put("date", format(dayFormat, showtimes.getShowdate()));
            row.put("time", format(minuteFormat, showtimes.getShowtime()));
            row.put("movie", movieName);
            row.put("cinema", cinemaName);
            row.put("hall", name(showtimes.getHall() == null ? null : showtimes.getHall().getHallName(), "（影厅缺失）"));
            row.put("seats", seats);
            row.put("sold", counts[SOLD]);
            row.put("free", counts[FREE]);
            row.put("damaged", counts[DAMAGED]);
            row.put("noSeat", counts[NO_SEAT]);
            row.put("unknown", counts[UNKNOWN]);
            row.put("rate", rate(counts[SOLD], seats));
            rows.add(row);
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("total", toMap(total));
        result.put("rows", rows);
        result.put("byMovie", toList(byMovie));
        result.put("byCinema", toList(byCinema));
        return result;
    }

    private static void accumulate(Map<String, Group> groups, String key, int seats, int sold, int damaged) {
        Group group = groups.get(key);
        if (group == null) {
            group = new Group();
            groups.put(key, group);
        }
        group.screenings++;
        group.seats += seats;
        group.sold += sold;
        group.damaged += damaged;
    }

    private static Object divide(Object numerator, Object denominator) {
        if (!(numerator instanceof Number) || !(denominator instanceof Number)) {
            return null;
        }
        double count = ((Number) denominator).doubleValue();
        if (count == 0) {
            return null;
        }
        return Math.round(((Number) numerator).doubleValue() / count * 100) / 100.0;
    }

    private static Object rate(int sold, int seats) {
        if (seats <= 0) {
            return null;
        }
        return Math.round(sold * 10000.0 / seats) / 100.0;
    }

    private static String name(String value, String fallback) {
        // 库里有影片名带 \r\n 的，不 trim 会把图表标签弄脏
        String trimmed = value == null ? "" : value.trim();
        return trimmed.isEmpty() ? fallback : trimmed;
    }

    private static String format(SimpleDateFormat format, Date date) {
        return date == null ? "" : format.format(date);
    }

    private static Map<String, Object> span(Map<String, Object> global) {
        Map<String, Object> span = new LinkedHashMap<>();
        span.put("min", global.get("minDate"));
        span.put("max", global.get("maxDate"));
        return span;
    }

    private static Map<String, Object> toMap(Group group) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("screenings", group.screenings);
        map.put("seats", group.seats);
        map.put("sold", group.sold);
        map.put("damaged", group.damaged);
        map.put("unknown", group.unknown);
        map.put("rate", rate(group.sold, group.seats));
        return map;
    }

    private static List<Map<String, Object>> toList(Map<String, Group> groups) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Map.Entry<String, Group> entry : groups.entrySet()) {
            Map<String, Object> map = toMap(entry.getValue());
            map.put("name", entry.getKey());
            list.add(map);
        }
        list.sort((a, b) -> Double.compare(number(b, "sold"), number(a, "sold")));
        return list;
    }

    private static double number(Map<String, Object> map, String key) {
        Object raw = map.get(key);
        return raw instanceof Number ? ((Number) raw).doubleValue() : 0;
    }

    private static class Group {
        int screenings;
        int seats;
        int sold;
        int damaged;
        int unknown;
    }
}
