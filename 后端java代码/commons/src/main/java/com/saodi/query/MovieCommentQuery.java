package com.saodi.query;

import lombok.Data;

/**
 * 后台影评管理的查询条件；条件为空则不参与过滤。
 */
@Data
public class MovieCommentQuery extends BaseQuery {
    private Integer movieId;
    private String movieName;
    private String userName;
    private String content;
}
