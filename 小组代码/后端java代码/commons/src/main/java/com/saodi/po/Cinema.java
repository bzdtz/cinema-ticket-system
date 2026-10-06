package com.saodi.po;

import com.baomidou.mybatisplus.annotation.TableField;

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
public class Cinema implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    private String account;

    /**
     * 影院名称
     */
    private String name;

    /**
     * 电话
     */
    private String phone;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 县
     */
    private String country;

    /**
     * 详细地址
     */
    private String specifiedAddress;

    /**
     * 标签
     */
    private String tag;

    /**
     * 最低价格
     */
    private String price;

    /**
     * 类型
     */
    private String type;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 服务
     */
//    @TableField(exist = false,typeHandler = org.apache.ibatis.type.BlobTypeHandler.class)
    private String service;

    /**
     * 头像图片
     */
    private String img;

    /**
     * 影院图片
     */
    private String pic;

    @TableField(exist = false)
    private  String cname;


    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public String getSpecifiedAddress() {
        return specifiedAddress;
    }

    public void setSpecifiedAddress(String specifiedAddress) {
        this.specifiedAddress = specifiedAddress;
    }
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "Cinema{" +
            "id=" + id +
            ", account=" + account +
            ", name=" + name +
            ", phone=" + phone +
            ", province=" + province +
            ", city=" + city +
            ", country=" + country +
            ", specifiedAddress=" + specifiedAddress +
            ", tag=" + tag +
            ", price=" + price +
            ", type=" + type +
            ", brand=" + brand +
            ", service=" + service +
            ", img=" + img +
            ", pic=" + pic +
        "}";
    }
}
