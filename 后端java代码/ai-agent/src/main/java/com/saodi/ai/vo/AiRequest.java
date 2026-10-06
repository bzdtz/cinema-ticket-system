package com.saodi.ai.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  智能体的一次提问。context 是浮窗所在页面的信息，让「这场怎么样」这种指代能落到具体场次上。
 * </p>
 *
 * @author saodi
 */
@Data
public class AiRequest {

    private String message;

    /** 前端带的历史，形如 [{role:'user'|'assistant', content:'...'}]，只取最近若干条 */
    private List<Map<String, String>> history;

    /** 当前页面上下文：cinemaId / movieId / showtimeId，都可以为空 */
    private Map<String, Object> context;
}
