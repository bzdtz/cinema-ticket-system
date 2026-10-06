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

    private String baseUrl = "https://api.deepseek.com";

    private String model = "deepseek-chat";

    /** 一次提问最多允许模型来回调用几轮工具 */
    private int maxToolRounds = 5;

    private int timeoutMs = 30000;

    public boolean modelEnabled() {
        return apiKey != null && !apiKey.trim().isEmpty();
    }
}
