package com.saodi.query;

import lombok.Data;

@Data
public class OrderQuery  extends BaseQuery{

    private  Integer startIndex;
    private  String  name;
    private  Integer userId;
    private  Integer cinemaId;
    private  Integer orderId;
}
