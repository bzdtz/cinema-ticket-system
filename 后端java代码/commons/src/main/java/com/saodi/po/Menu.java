package com.saodi.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 路由
     */
    private String route;

    /**
     * component
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 层级
     */
    private String level;

    /**
     * 介绍
     */
    private String description;

    /**
     * 状态
     */
    private String status;
    /**
     * 子菜单
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private List<Menu> menuList = new ArrayList<>();


    /**
     * 父id
     */
    private Integer parentId;

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
    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }


    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + id +
            ", name=" + name +
            ", route=" + route +
            ", component=" + component +
            ", icon=" + icon +
            ", level=" + level +
            ", description=" + description +
            ", status=" + status +
            ", parentId=" + parentId +
        "}";
    }
}
