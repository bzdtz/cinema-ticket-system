package com.saodi.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  模型客户端。走 OpenAI 兼容的 /chat/completions，所以 baseUrl 换成哪家都能用，
 *  唯一的硬要求是对方支持 tools / tool_calls —— 智能体的工具层全靠这个。
 *  只做一次性（非流式）请求：Java 8 里撸 SSE 不值当，前端用打字机效果补一下就行。
 * </p>
 *
 * @author saodi
 */
@Component
public class LlmClient {

    @Autowired
    private AiProperties props;

    private final ObjectMapper mapper = new ObjectMapper();

    // 评测第一轮 19/30 退到规则兜底，查出来是这家提供方限流不走 429，而是 HTTP 400 +
    // code -20048「请求过于频繁」。当成普通失败往外抛，整条链路就塌到兜底那版，
    // 所以这里认出限流、等一小会儿再试。只认限流，别的错误一律照旧抛。
    private static final int THROTTLE_RETRY_LIMIT = 2;
    private static final long[] THROTTLE_BACKOFF_MS = {2500L, 6000L};
    // 上一次请求已经耗到这个秒数就不再重试了：模型调用挂住时，重试只会把用户等得更久
    private static final long THROTTLE_RETRY_BUDGET_MS = 15000L;

    public ChatMessage chat(List<Map<String, Object>> messages, List<Map<String, Object>> tools) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("model", props.getModel());
        body.put("messages", messages);
        body.put("temperature", 0.3);
        if (tools != null && !tools.isEmpty()) {
            body.put("tools", tools);
            body.put("tool_choice", "auto");
        }

        String json;
        try {
            json = mapper.writeValueAsString(body);
        } catch (Exception e) {
            throw new LlmException("调用模型失败：" + e.getClass().getSimpleName() + " " + e.getMessage(), e);
        }

        long started = System.currentTimeMillis();
        for (int attempt = 0; ; attempt++) {
            try {
                String responseText = post(props.getBaseUrl() + "/chat/completions", json);
                return parse(mapper.readTree(responseText));
            } catch (LlmException e) {
                if (!isThrottled(e.getMessage()) || attempt >= THROTTLE_RETRY_LIMIT
                        || System.currentTimeMillis() - started > THROTTLE_RETRY_BUDGET_MS) {
                    throw e;
                }
                sleep(THROTTLE_BACKOFF_MS[attempt]);
            } catch (Exception e) {
                throw new LlmException("调用模型失败：" + e.getClass().getSimpleName() + " " + e.getMessage(), e);
            }
        }
    }

    private static boolean isThrottled(String message) {
        if (message == null) {
            return false;
        }
        // 大小写归一：提供方给的英文串可能是 "Too Many Requests"，也可能是小写的 rate limit。
        String text = message.toLowerCase();
        return message.contains("请求过于频繁") || message.contains("模型返回 429")
                || text.contains("-20048") || text.contains("too many requests")
                || text.contains("rate limit");
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new LlmException("等限流重试的时候被打断了", e);
        }
    }

    private String post(String url, String jsonBody) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(props.getTimeoutMs());
        conn.setReadTimeout(props.getTimeoutMs());
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + props.getApiKey());

        try (OutputStream out = conn.getOutputStream()) {
            out.write(jsonBody.getBytes(StandardCharsets.UTF_8));
        }

        int status = conn.getResponseCode();
        InputStream stream = status >= 400 ? conn.getErrorStream() : conn.getInputStream();
        String text = read(stream);
        if (status >= 400) {
            // 只带响应体，绝不把请求头或 key 拼进异常信息
            throw new LlmException("模型返回 " + status + "：" + brief(text));
        }
        return text;
    }

    private static ChatMessage parse(JsonNode root) {
        JsonNode choice = root.path("choices").path(0).path("message");
        ChatMessage message = new ChatMessage();
        message.setContent(choice.path("content").isMissingNode() ? null : choice.path("content").asText(""));
        for (JsonNode call : choice.path("tool_calls")) {
            ToolCall toolCall = new ToolCall();
            toolCall.setId(call.path("id").asText(""));
            toolCall.setName(call.path("function").path("name").asText(""));
            toolCall.setArgumentsJson(call.path("function").path("arguments").asText("{}"));
            message.getToolCalls().add(toolCall);
        }
        return message;
    }

    private static String read(InputStream stream) throws Exception {
        if (stream == null) {
            return "";
        }
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] chunk = new byte[4096];
        int read;
        while ((read = stream.read(chunk)) > 0) {
            buffer.write(chunk, 0, read);
        }
        return new String(buffer.toByteArray(), StandardCharsets.UTF_8);
    }

    private static String brief(String text) {
        String flat = text.replaceAll("\\s+", " ").trim();
        return flat.length() > 300 ? flat.substring(0, 300) + "…" : flat;
    }

    public static class ChatMessage {
        private String content = "";
        private final List<ToolCall> toolCalls = new ArrayList<>();

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<ToolCall> getToolCalls() {
            return toolCalls;
        }

        public boolean wantsTools() {
            return !toolCalls.isEmpty();
        }
    }

    public static class ToolCall {
        private String id;
        private String name;
        private String argumentsJson;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getArgumentsJson() {
            return argumentsJson;
        }

        public void setArgumentsJson(String argumentsJson) {
            this.argumentsJson = argumentsJson;
        }
    }

    public static class LlmException extends RuntimeException {
        public LlmException(String message) {
            super(message);
        }

        public LlmException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
