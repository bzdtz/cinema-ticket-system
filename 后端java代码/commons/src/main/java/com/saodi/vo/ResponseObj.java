package com.saodi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObj<T>{

    private Integer code;
    private String msg;
    private T data;

    public static <T> ResponseObj<T> SUCCESS(){
        return new ResponseObj(200,"success",null);
    }

    public static <T> ResponseObj<T> SUCCESS(String msg){
        return new ResponseObj(200,msg,null);
    }

    public static <T> ResponseObj<T> SUCCESS(String msg,T data){
        return new ResponseObj(200,msg,data);
    }

    public static <T> ResponseObj<T> SUCCESS(Object data){
        return new ResponseObj(200,"success",data);
    }

    public static <T> ResponseObj<T> ERROR(){
        return new ResponseObj(500,"error",null);
    }

    public static <T> ResponseObj<T> ERROR(Integer code, String msg){
        return new ResponseObj(code,msg,null);
    }

}
