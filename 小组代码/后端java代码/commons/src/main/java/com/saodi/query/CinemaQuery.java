package com.saodi.query;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class CinemaQuery extends BaseQuery  implements Serializable {
    String name;
    String province;
    String city;
    String country;
    String specifiedAddress;
    String tag;
    Integer priceMax;
    Integer priceMin;
    String type;

    String brand;




}
