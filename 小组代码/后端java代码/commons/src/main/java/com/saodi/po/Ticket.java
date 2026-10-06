package com.saodi.po;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 取票码
     */
    private String ticketCode;

    /**
     * 订单id
     */
    private Integer orderId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Ticket{" +
            "id=" + id +
            ", ticketCode=" + ticketCode +
            ", orderId=" + orderId +
        "}";
    }
}
