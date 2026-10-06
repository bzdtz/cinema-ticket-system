package com.saodi.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("`order`")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 状态
     */
    private String status;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 支付id
     */
    private Integer payId;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss" ,timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date payTime;

    /**
     * 最后确认时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm", timezone = "GMT+8")
    private Date lastConfirmTime;

    private Integer userId;

    @TableField(exist = false)
    private  User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getLastConfirmTime() {
        return lastConfirmTime;
    }

    public void setLastConfirmTime(Date lastConfirmTime) {
        this.lastConfirmTime = lastConfirmTime;
    }

    /**
     * 排片id
     */

    private Integer showtimesId;
    @TableField(exist = false)
    private  Showtimes showtimes;

    @TableField(exist = false)
    private Cinema cinema;

    @TableField(exist = false)
    private  Movie movie;

    @TableField(exist = false)
    private Hall  hall;

    public Cinema getCinema() {
        return cinema;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", status='" + status + '\'' +
                ", totalPrice=" + totalPrice +
                ", orderId=" + orderId +
                ", payId=" + payId +
                ", payTime=" + payTime +
                ", lastConfirmTime=" + lastConfirmTime +
                ", userId=" + userId +
                ", user=" + user +
                ", showtimesId=" + showtimesId +
                ", showtimes=" + showtimes +
                ", cinema=" + cinema +
                ", movie=" + movie +
                ", hall=" + hall +
                '}';
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Showtimes getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(Showtimes showtimes) {
        this.showtimes = showtimes;
    }


    @TableField(exist = false)
    private  String seat;

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    @TableField(exist = false)
    private  String moviename;

    public Integer getMovieid() {
        return movieid;
    }

    public void setMovieid(Integer movieid) {
        this.movieid = movieid;
    }

    @TableField(exist = false)
    private  Integer movieid;

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getShowtimesId() {
        return showtimesId;
    }

    public void setShowtimesId(Integer showtimesId) {
        this.showtimesId = showtimesId;
    }

}
