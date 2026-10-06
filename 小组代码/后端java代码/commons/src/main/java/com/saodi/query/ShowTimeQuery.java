package com.saodi.query;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@Data
public class ShowTimeQuery extends BaseQuery {
    private Integer cinemaId;
    private Integer movieId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String showTime;

    //    排片id
    Integer id;
    //    起止时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    String start;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    String end;
    //    moviename
    String movieName;
    //    cinemaname
    String cinemaName;
    //    限制价格
    Integer startPrice;
    Integer endPrice;
    //    查询已经结束or未开始or正在播出
    Integer status;

}
