package com.saodi.ai.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  智能体的回答。draft 非空时前端才出现「就按这个下单」，点了也只是跳转预填，不落库。
 * </p>
 *
 * @author saodi
 */
@Data
public class AiReply {

    private String answer;

    /** deepseek=真的走了模型；rule=按规则查库回答（没配 key 或模型调用失败） */
    private String engine;

    private Map<String, Object> draft;

    /** 这一轮实际调用过哪些工具，前端折叠展示，便于判断它是不是在瞎编 */
    private List<String> steps = new ArrayList<>();
}
