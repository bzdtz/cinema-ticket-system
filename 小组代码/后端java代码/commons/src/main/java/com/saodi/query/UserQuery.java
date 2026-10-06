package com.saodi.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuery extends BaseQuery {
    private String username;
    private  String phone;
    private String email;
}
