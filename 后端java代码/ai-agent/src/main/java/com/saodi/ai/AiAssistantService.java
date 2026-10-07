package com.saodi.ai;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saodi.ai.tool.ToolContext;
import com.saodi.ai.tool.ToolRegistry;
import com.saodi.ai.vo.AiReply;
import com.saodi.ai.vo.AiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  智能体主流程：带工具问模型，模型要工具就执行、把结果喂回去，直到它给出最终回答。
 *  没配 key 或者模型叫不通时，整条链路退到 RuleAssistant，功能不断。
 * </p>
 *
 * @author saodi
 */
@Service
public class AiAssistantService {

    @Autowired
    private AiProperties props;
    @Autowired
    private LlmClient client;
    @Autowired
    private ToolRegistry registry;
    @Autowired
    private RuleAssistant ruleAssistant;

    private final ObjectMapper mapper = new ObjectMapper();

    public AiReply chat(AiRequest request) {
        if (!props.modelEnabled()) {
            return ruleAssistant.answer(request);
        }
        try {
            return run(request);
        } catch (Exception e) {
            AiReply fallback = ruleAssistant.answer(request);
            fallback.setAnswer("（模型这次没叫通：" + brief(e.getMessage())
                    + "，下面这版是直接查库拼出来的）\n" + fallback.getAnswer());
            return fallback;
        }
    }

    private AiReply run(AiRequest request) throws Exception {
        List<Map<String, Object>> messages = new ArrayList<>();
        messages.add(message("system", systemPrompt(request)));
        messages.addAll(history(request));
        messages.add(message("user", request.getMessage() == null ? "" : request.getMessage()));

        // 身份从登录态来，不从模型的文本来：模型能决定查什么，决定不了以谁的身份查
        ToolContext context = new ToolContext(request.getUserId());

        AiReply reply = new AiReply();
        // engine 报"是哪个模型答的"，不是写死的厂商名。兜底路径那边报 "rule"，
        // 前端靠这个值区分「模型」和「规则兜底」，所以这里必须给真模型名。
        reply.setEngine(props.getModel());

        for (int round = 0; round < props.getMaxToolRounds(); round++) {
            LlmClient.ChatMessage answer = client.chat(messages, registry.definitions());
            if (!answer.wantsTools()) {
                reply.setAnswer(answer.getContent());
                return reply;
            }
            messages.add(assistantToolCallMessage(answer));
            for (LlmClient.ToolCall call : answer.getToolCalls()) {
                Map<String, Object> args = call.getArgumentsJson() == null || call.getArgumentsJson().trim().isEmpty()
                        ? new LinkedHashMap<>()
                        : mapper.readValue(call.getArgumentsJson(), new TypeReference<Map<String, Object>>() {
            });
                String outcome = registry.run(call.getName(), args, context);
                List<String> steps = reply.getSteps();
                int last = steps.size() - 1;
                if (last >= 0 && steps.get(last).startsWith(call.getName())) {
                    int at = steps.get(last).indexOf('×');
                    int times = at < 0 ? 1 : Integer.parseInt(steps.get(last).substring(at + 1));
                    steps.set(last, call.getName() + "×" + (times + 1));
                } else {
                    steps.add(call.getName());
                }
                messages.add(message("tool", withholdToken(outcome), call.getId()));
                if ("draft_order".equals(call.getName())) {
                    captureDraft(reply, outcome);
                }
            }
        }

        // 模型在工具里绕圈子绕到上限，别丢下一句"你问得太粗"就收场——直接查库把结论补上
        AiReply grounded = ruleAssistant.answer(request);
        reply.setEngine("rule");
        reply.setAnswer("（模型绕了 " + props.getMaxToolRounds() + " 轮没收口，这版是直接查库拼出来的）\n"
                + grounded.getAnswer());
        if (reply.getDraft() == null) {
            reply.setDraft(grounded.getDraft());
        }
        return reply;
    }

    private String systemPrompt(AiRequest request) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("你是「码上影院」站内的选座助手。今天日期 ")
                .append(new SimpleDateFormat("yyyy-MM-dd").format(new Date())).append("。\n")
                .append("规则：\n")
                .append("1. 影片、影院、场次、余座、价格一律用工具查真实数据，查不到就说查不到，禁止编造 id 或数字。\n")
                .append("2. 你不能下单。draft_order 只产出草稿，用户必须自己在页面上点确认；不要说\"已下单\"这类话。\n")
                .append("   确认凭证不会出现在你看到的工具结果里，所以你也没有替用户提交的办法，别去猜。\n")
                // 评测里 E2「换两个别的连座，重新出一份草稿」只查了座位就照着上一轮的答复念了一遍
                // "草稿有效期 10 分钟"，页面上其实什么都没有。草稿只有真调了 draft_order 才存在。
                .append("   草稿只在本次真的调用 draft_order 之后才存在：上一轮那份不算这一轮的，")
                .append("没调过就不许提\"草稿已生成\"\"草稿有效期\"，要出草稿就先调再说。\n")
                .append("3. 工具返回的座位行列是 0 下标，讲给用户时必须 +1，说成\"X排Y座\"。\n")
                .append("4. 推荐影院前先确认它有排片，排片为 0 的影院点进去是空的。\n")
                .append("5. 用中文，120 字以内，列清单时一行一个。\n")
                .append("6. 别反复调同一个工具凑答案：seat_summary 一次只看一个场次，最多看 3 个场次就必须给结论；")
                .append("用户没指定场次时先用 find_showtimes 定一个，不要拿整部剧的场次挨个查。\n")
                // 评测里 D5「哪个场次人最少」被这条误伤：模型怕绕圈，改成反问用户要看哪部电影。
                // 少绕圈的正解不是不问用户，而是根本不用挨个 seat_summary——比较要用的字段 find_showtimes 已经带了。
                .append("   跨场次比较（哪个场次人最少、最便宜）不用挨个 seat_summary：")
                .append("find_showtimes 的返回里就带了 free、sold、price，一次查完排完序再给结论。\n")
                // 评测里 C5/B4 自己填了"今天"去查，库里排片全落在过去，就直接答了"查不到"
                .append("7. date 这类筛选条件是可选的：用户没给日期就别自己填今天的日期去查；")
                .append("查出来是空的，先把可选条件去掉重查一次，再回答没有。本站的排片日期可能落在过去，")
                .append("空结果先怀疑自己加的条件，不要当成库里没数据。\n")
                .append("8. 别把定参数的活推回给用户：能自己定默认值就直接查完给结论，")
                .append("只有缺了没法默认的关键信息才反问。\n");
        Map<String, Object> context = request.getContext();
        if (context != null && !context.isEmpty()) {
            prompt.append("用户当前页面的上下文：").append(context).append("。\n");
        }
        return prompt.toString();
    }

    private List<Map<String, Object>> history(AiRequest request) {
        if (request.getHistory() == null || request.getHistory().isEmpty()) {
            return Collections.emptyList();
        }
        List<Map<String, Object>> recent = new ArrayList<>();
        for (Map<String, String> entry : request.getHistory()) {
            String role = entry.get("role");
            String content = entry.get("content");
            if (content == null || content.trim().isEmpty()) {
                continue;
            }
            if ("user".equals(role) || "assistant".equals(role)) {
                recent.add(message(role, content));
            }
        }
        // 只留最近 6 条，历史太长会把工具轮次的预算挤掉
        return recent.subList(Math.max(0, recent.size() - 6), recent.size());
    }

    private static Map<String, Object> message(String role, String content) {
        Map<String, Object> message = new LinkedHashMap<>();
        message.put("role", role);
        message.put("content", content);
        return message;
    }

    private static Map<String, Object> message(String role, String content, String toolCallId) {
        Map<String, Object> message = message(role, content);
        message.put("tool_call_id", toolCallId);
        return message;
    }

    private static Map<String, Object> assistantToolCallMessage(LlmClient.ChatMessage answer) {
        List<Map<String, Object>> calls = new ArrayList<>();
        for (LlmClient.ToolCall call : answer.getToolCalls()) {
            Map<String, Object> function = new LinkedHashMap<>();
            function.put("name", call.getName());
            function.put("arguments", call.getArgumentsJson());

            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("id", call.getId());
            entry.put("type", "function");
            entry.put("function", function);
            calls.add(entry);
        }
        Map<String, Object> message = new LinkedHashMap<>();
        message.put("role", "assistant");
        message.put("content", answer.getContent() == null ? "" : answer.getContent());
        message.put("tool_calls", calls);
        return message;
    }

    /**
     * 确认凭证只交给浏览器，不进模型上下文——模型拿不到令牌，也就没有替用户点确认的途径。
     * 解析不动时宁可整条换成错误，也不把可能带令牌的原文回给模型。
     */
    private String withholdToken(String outcome) {
        if (outcome == null || !outcome.contains("confirmToken")) {
            return outcome;
        }
        try {
            Map<String, Object> payload = mapper.readValue(outcome,
                    new TypeReference<Map<String, Object>>() {
                    });
            payload.remove("confirmToken");
            return mapper.writeValueAsString(payload);
        } catch (Exception e) {
            return "{\"error\":\"草稿已生成，但工具结果没能回传给模型\"}";
        }
    }

    private void captureDraft(AiReply reply, String outcome) {
        try {
            Map<String, Object> draft = mapper.readValue(outcome,
                    new TypeReference<Map<String, Object>>() {
                    });
            if (Boolean.TRUE.equals(draft.get("ok"))) {
                reply.setDraft(draft);
            }
        } catch (Exception ignored) {
            // 草稿没拿到就算了，正文里模型会解释原因
        }
    }

    private static String brief(String message) {
        if (message == null || message.trim().isEmpty()) {
            return "未知错误";
        }
        String flat = message.replaceAll("\\s+", " ").trim();
        return flat.length() > 200 ? flat.substring(0, 200) + "…" : flat;
    }
}
