package com.saodi.query;

import lombok.Data;

@Data
public class CinemaUserQuery extends BaseQuery{

    private String username;
    private String email;
    private String phone;


}
