package com.saodi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserVo {
    private String id;
    private String username;
    private String password;
    private String captcha;
}
