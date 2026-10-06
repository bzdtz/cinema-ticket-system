package com.saodi.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public class Showtimesdetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 排片id
     */
    private Integer showtimesId;

    /**
     * 简介
     */
    private String intro;

    /**
     * 下单须知
     */
    private String notes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getShowtimesId() {
        return showtimesId;
    }

    public void setShowtimesId(Integer showtimesId) {
        this.showtimesId = showtimesId;
    }
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Showtimesdetail{" +
            "id=" + id +
            ", showtimesId=" + showtimesId +
            ", intro=" + intro +
            ", notes=" + notes +
        "}";
    }
}
