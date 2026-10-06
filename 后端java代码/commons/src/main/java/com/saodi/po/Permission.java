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
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 名字
     */
    private String name;

    /**
     * url
     */
    private String url;

    /**
     * 介绍
     */
    private String description;

    /**
     * sn
     */
    private String sn;

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
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "Permission{" +
            "id=" + id +
            ", name=" + name +
            ", url=" + url +
            ", description=" + description +
            ", sn=" + sn +
        "}";
    }
}
