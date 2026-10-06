package com.saodi.ai.tool;

import java.util.Map;

/**
 * <p>
 *  智能体的一个工具。工具直接调 commons 里现成的 service，不重新实现业务查询，
 *  这样权限和数据口径跟页面走的是同一套代码。
 * </p>
 *
 * @author saodi
 */
public interface AgentTool {

    String name();

    String description();

    /** OpenAI tools 协议里的 parameters，即一段 JSON Schema */
    Map<String, Object> parameters();

    Object execute(Map<String, Object> args);
}
