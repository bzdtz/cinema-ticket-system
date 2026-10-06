package com.saodi.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieQuery  extends BaseQuery{

    /**
     * 电影类型的Id
     */
    private  Integer typeId;


    /**
     * 电影上映的国家
     */
    private  String  region;

    /**
     * 电影上映的年份
     */

    private  String year;

    /**
     * 电影的热度 根据票房来判断
     */
    private  Integer boxOffice;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    private Date releaseTime;


    /**
     * 根据评分
     */
    private  Double score;

    /**
     * 根据名称来模糊搜索
     */
    private String  name;

    @Override
    public String toString() {
        return "MovieQuery{" +
                "typeId=" + typeId +
                ", region='" + region + '\'' +
                ", year='" + year + '\'' +
                ", boxOffice=" + boxOffice +
                ", releaseTime=" + releaseTime +
                ", score=" + score +
                ", name='" + name + '\'' +
                "} " + super.toString();
    }
}
