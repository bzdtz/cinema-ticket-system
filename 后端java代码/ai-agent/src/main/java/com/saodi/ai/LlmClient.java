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
 *  DeepSeek 客户端。走 OpenAI 兼容的 /chat/completions，所以 baseUrl 换成智谱、Kimi 也能用。
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

    public ChatMessage chat(List<Map<String, Object>> messages, List<Map<String, Object>> tools) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("model", props.getModel());
        body.put("messages", messages);
        body.put("temperature", 0.3);
        if (tools != null && !tools.isEmpty()) {
            body.put("tools", tools);
            body.put("tool_choice", "auto");
        }

        try {
            String responseText = post(props.getBaseUrl() + "/chat/completions",
                    mapper.writeValueAsString(body));
            return parse(mapper.readTree(responseText));
        } catch (LlmException e) {
            throw e;
        } catch (Exception e) {
            throw new LlmException("调用模型失败：" + e.getClass().getSimpleName() + " " + e.getMessage(), e);
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
