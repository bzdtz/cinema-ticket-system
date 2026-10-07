package com.saodi.ai.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  工具注册表。Spring 会把所有 AgentTool 实现注入进来，加工具不需要改这里。
 * </p>
 *
 * @author saodi
 */
@Component
public class ToolRegistry {

    private final Map<String, AgentTool> tools = new LinkedHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    public ToolRegistry(List<AgentTool> discovered) {
        for (AgentTool tool : discovered) {
            tools.put(tool.name(), tool);
        }
    }

    /** 发给模型的 tools 字段 */
    public List<Map<String, Object>> definitions() {
        List<Map<String, Object>> definitions = new ArrayList<>();
        for (AgentTool tool : tools.values()) {
            Map<String, Object> function = new LinkedHashMap<>();
            function.put("name", tool.name());
            function.put("description", tool.description());
            function.put("parameters", tool.parameters());

            Map<String, Object> definition = new LinkedHashMap<>();
            definition.put("type", "function");
            definition.put("function", function);
            definitions.add(definition);
        }
        return definitions;
    }

    /**
     * 执行工具并序列化成给模型的 tool 消息内容。工具报错不抛出去，
     * 而是把错误原样回给模型，让它自己换个参数重试。
     *
     * context 是服务端塞进来的（当前是谁在问），不经过模型，模型也改不了它。
     */
    public String run(String name, Map<String, Object> args, ToolContext context) {
        AgentTool tool = tools.get(name);
        if (tool == null) {
            return "{\"error\":\"没有这个工具：" + name + "\"}";
        }
        try {
            return mapper.writeValueAsString(
                    tool.execute(args == null ? new LinkedHashMap<>() : args, context));
        } catch (Exception e) {
            String reason = e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
            return "{\"error\":\"" + name + " 执行失败：" + reason.replace('"', '\'') + "\"}";
        }
    }

    public List<String> names() {
        return new ArrayList<>(tools.keySet());
    }
}
