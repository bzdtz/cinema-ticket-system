package com.saodi.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
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
public class Showtimes implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO,value = "id")
    private Integer id;

    /**
     * 电影id
     */
    private Integer movieId;


    @TableField(exist = false)
    private Movie movie;


    /**
     * 影院id
     */
    private Integer cinemaId;


    @TableField(exist = false)
    private  Cinema cinema;



    /**
     * 影厅id
     */
    private Integer hallId;

    @TableField(exist = false)
    private Hall  hall;

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    /**
     * 放映时间
     */
    @DateTimeFormat(pattern = "HH:mm")
    @JsonFormat(pattern = "HH:mm:",timezone = "GMT+8")
    private Time showtime;

    /**
     * 放映日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date showdate;

    /**
     * 售价
     */
    private BigDecimal sale;

    /**
     * 座位
     */
    private String seat;
    @TableField(exist = false)
    private String endtime;

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }
    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public Time getShowtime() {
        return showtime;
    }

    public void setShowtime(Time showtime) {
        this.showtime = showtime;
    }

    public Date getShowdate() {
        return showdate;
    }

    public void setShowdate(Date showdate) {
        this.showdate = showdate;
    }

    public BigDecimal getSale() {
        return sale;
    }

    public void setSale(BigDecimal sale) {
        this.sale = sale;
    }
    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }
    @Override
    public String toString() {
        return "Showtimes{" +
            "id=" + id +
            ", movieId=" + movieId +
            ", cinemaId=" + cinemaId +
            ", hallId=" + hallId +
            ", showtime=" + showtime +
            ", showdate=" + showdate +
            ", sale=" + sale +
            ", seat=" + seat +
        "}";
    }
}
