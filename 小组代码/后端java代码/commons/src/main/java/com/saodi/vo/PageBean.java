package com.saodi.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageBean<T> {
    private  Integer totalRows;
    private Integer totalPages;
    private List<T> data;
}
