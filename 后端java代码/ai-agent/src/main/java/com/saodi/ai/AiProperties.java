package com.saodi.ai;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  智能体的模型接入配置。api-key 只留在后端，任何响应里都不会带出去。
 * </p>
 *
 * @author saodi
 */
@Data
@Component
@ConfigurationProperties(prefix = "ai")
public class AiProperties {

    /** 留空则整个智能体退化成规则兜底，功能照常可用 */
    private String apiKey = "";

    /** OpenAI 兼容端点的基址，客户端在后面拼 /chat/completions */
    private String baseUrl = "https://chat.intern-ai.org.cn/api/v1";

    /** 前端引擎标签显示的就是这个值，所以改配置等于换模型，不用动代码 */
    private String model = "intern-s2-preview";

    /** 一次提问最多允许模型来回调用几轮工具 */
    private int maxToolRounds = 5;

    /** 草稿确认凭证的有效期：过了就得重新要一份，免得座位状态早变了还按老草稿成交 */
    private int draftTtlSeconds = 600;

    private int timeoutMs = 30000;

    public boolean modelEnabled() {
        return apiKey != null && !apiKey.trim().isEmpty();
    }
}
