package com.saodi.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import javafx.scene.control.Tab;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 海报
     */
    private String banner;

    /**
     * 区域
     */
    private String region;

    /**
     * 电影播放时长
     */
    private Integer movieLength;

    /**
     * 评分
     */
    private Double score;

    /**
     * 票房
     */
    private Integer boxOffice;

    /**
     * 剧情简介
     */
    private String synopsis;

    /**
     * 电影语言版本
     */
    private String langue;

    /**
     * 上映时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm", timezone = "GMT+8")
    private Date releaseTime;

    /**
     * 想看人数
     */
    private Integer wantNumber;

    /**
     * 奖项
     */
    private String awards;

    /**
     * 出品发行商
     */
    private String publisher;


    /**
     * 首页显示的电影标签
     */
    @TableField(exist = false)
    private Tag tag;

    @TableField(exist = false)
    private Movietype movietype;

    @TableField(exist = false)
    private MovieTypeMapping movieTypeMapping;

    @TableField(exist = false)
    private  Cinema  cinema;

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Movietype getMovietype() {
        return movietype;
    }

    public void setMovietype(Movietype movietype) {
        this.movietype = movietype;
    }

    public MovieTypeMapping getMovieTypeMapping() {
        return movieTypeMapping;
    }

    public void setMovieTypeMapping(MovieTypeMapping movieTypeMapping) {
        this.movieTypeMapping = movieTypeMapping;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getMovieLength() {
        return movieLength;
    }

    public void setMovieLength(Integer movieLength) {
        this.movieLength = movieLength;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(Integer boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Integer getWantNumber() {
        return wantNumber;
    }

    public void setWantNumber(Integer wantNumber) {
        this.wantNumber = wantNumber;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", banner='" + banner + '\'' +
                ", region='" + region + '\'' +
                ", movieLength=" + movieLength +
                ", score=" + score +
                ", boxOffice=" + boxOffice +
                ", synopsis='" + synopsis + '\'' +
                ", langue='" + langue + '\'' +
                ", releaseTime=" + releaseTime +
                ", wantNumber=" + wantNumber +
                ", awards='" + awards + '\'' +
                ", publisher='" + publisher + '\'' +

                ", tag=" + tag +
                ", movietype=" + movietype +
                ", movieTypeMapping=" + movieTypeMapping +
                '}';
    }
}
