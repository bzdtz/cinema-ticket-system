package com.saodi.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class Hall implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 影院Id
     */
    private Integer cinemaId;

    /**
     * 影院名字
     */

    private String hallName;

    /**
     * 影厅尺寸
     */
    private String hallSize;

    @TableField(exist = false)
    private String cinemaName;


    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }
    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }
    public String getHallSize() {
        return hallSize;
    }

    public void setHallSize(String hallSize) {
        this.hallSize = hallSize;
    }

    @Override
    public String toString() {
        return "Hall{" +
            "id=" + id +
            ", cinemaId=" + cinemaId +
            ", hallName=" + hallName +
            ", hallSize=" + hallSize +
        "}";
    }
}
