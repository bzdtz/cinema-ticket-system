package com.saodi.ai.tool;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodi.po.Cinema;
import com.saodi.po.Hall;
import com.saodi.po.Movie;
import com.saodi.po.Showtimes;
import com.saodi.service.ICinemaService;
import com.saodi.service.IHallService;
import com.saodi.service.IMovieService;
import com.saodi.service.IShowtimesService;
import com.saodi.util.SeatMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 *  场次读取的公共层。Showtimes 上的 movie/cinema/hall 关联字段不会被单表查询填出来，
 *  所以这里按 id 反查并缓存，避免每个工具各写一遍 join。
 * </p>
 *
 * @author saodi
 */
@Component
public class ShowtimeReader {

    @Autowired
    private IShowtimesService showtimesService;
    @Autowired
    private IMovieService movieService;
    @Autowired
    private ICinemaService cinemaService;
    @Autowired
    private IHallService hallService;

    private final Map<Integer, String> movieNames = new LinkedHashMap<>();
    private final Map<Integer, String> cinemaNames = new LinkedHashMap<>();
    private final Map<Integer, String> hallNames = new LinkedHashMap<>();

    /**
     * service 的 getById 是 INNER JOIN cinema/movie/hall 的，库里留着几条指向已删影院的场次
     * （22/24/25/27），那种场次 join 不出来但座位数据是完整的，所以退一步按主键直查，
     * 名字由 resolve() 标成「已删除」。
     */
    public Showtimes find(Integer id) {
        if (id == null) {
            return null;
        }
        Showtimes joined = showtimesService.getById(id);
        if (joined != null) {
            return joined;
        }
        return showtimesService.getOne(new QueryWrapper<Showtimes>().eq("id", id), false);
    }

    /** 一行场次的紧凑描述，座位矩阵只留统计数字，不把整串塞给模型 */
    public Map<String, Object> describe(Showtimes showtimes) {
        int[] counts = SeatMatrix.count(showtimes.getSeat());
        int seats = counts[SeatMatrix.FREE] + counts[SeatMatrix.SOLD] + counts[SeatMatrix.DAMAGED];

        Map<String, Object> row = new LinkedHashMap<>();
        row.put("id", showtimes.getId());
        row.put("movie", movieName(showtimes.getMovieId()));
        row.put("cinema", cinemaName(showtimes.getCinemaId()));
        row.put("hall", hallName(showtimes.getHallId()));
        row.put("date", format(showtimes.getShowdate(), "yyyy-MM-dd"));
        row.put("time", format(showtimes.getShowtime(), "HH:mm"));
        row.put("price", showtimes.getSale());
        row.put("seats", seats);
        row.put("free", counts[SeatMatrix.FREE]);
        row.put("sold", counts[SeatMatrix.SOLD]);
        row.put("damaged", counts[SeatMatrix.DAMAGED]);
        return row;
    }

    public String movieName(Integer id) {
        return resolve(movieNames, id, () -> {
            Movie movie = movieService.getById(id);
            return movie == null ? null : movie.getName();
        });
    }

    public String cinemaName(Integer id) {
        return resolve(cinemaNames, id, () -> {
            Cinema cinema = cinemaService.getById(id);
            return cinema == null ? null : cinema.getName();
        });
    }

    public String hallName(Integer id) {
        return resolve(hallNames, id, () -> {
            Hall hall = hallService.getById(id);
            return hall == null ? null : hall.getHallName();
        });
    }

    public java.util.List<Showtimes> list(QueryWrapper<Showtimes> wrapper) {
        return showtimesService.list(wrapper);
    }

    private static String resolve(Map<Integer, String> cache, Integer id, Loader loader) {
        if (id == null) {
            return "（缺失）";
        }
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        String value = loader.get();
        String name = value == null || value.trim().isEmpty() ? "（已删除 #" + id + "）" : value.trim();
        cache.put(id, name);
        return name;
    }

    private static String format(Date date, String pattern) {
        return date == null ? "" : new SimpleDateFormat(pattern).format(date);
    }

    private interface Loader {
        String get();
    }
}
