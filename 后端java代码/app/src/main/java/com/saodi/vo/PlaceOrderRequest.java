package com.saodi.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  下单请求。只有「哪一场、订哪几个座、（可选）一张确认凭证」这三样。
 *  价格、座位矩阵、订单主键都由服务端算，客户端传什么都不认。
 * </p>
 *
 * @author saodi
 */
public class PlaceOrderRequest {

    private Integer showtimesId;

    /** [[行,列],...]，0 下标，和智能体草稿里的 seats 同一种写法 */
    private List<List<Integer>> seats = new ArrayList<>();

    /** 智能体草稿签发的一次性凭证；手动选座时为空 */
    private String confirmToken;

    public Integer getShowtimesId() {
        return showtimesId;
    }

    public void setShowtimesId(Integer showtimesId) {
        this.showtimesId = showtimesId;
    }

    public List<List<Integer>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<Integer>> seats) {
        this.seats = seats;
    }

    public String getConfirmToken() {
        return confirmToken;
    }

    public void setConfirmToken(String confirmToken) {
        this.confirmToken = confirmToken;
    }
}
