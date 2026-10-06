package com.saodi.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class DateCinemaQuery  extends  BaseQuery{
    private  String brand;
    private String  city;
    private String country;
    private  String type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private  String userDate;
    private  Integer mid;

    private Integer  startIndex;

}
