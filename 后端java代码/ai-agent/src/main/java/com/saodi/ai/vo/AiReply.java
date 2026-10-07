package com.saodi.ai.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  智能体的回答。draft 非空时前端才出现「就按这个下单」：跳过去预填座位，
 *  用户在页面上点确认才真的落库。draft 里的 confirmToken 只出现在这份 HTTP 响应里，
 *  喂回模型的那份已经剥掉了。
 * </p>
 *
 * @author saodi
 */
@Data
public class AiReply {

    private String answer;

    /** 走的是模型就把模型名写在这（如 deepseek-v4-flash）；rule=按规则查库兜底 */
    private String engine;

    private Map<String, Object> draft;

    /** 这一轮实际调用过哪些工具，前端折叠展示，便于判断它是不是在瞎编 */
    private List<String> steps = new ArrayList<>();
}
