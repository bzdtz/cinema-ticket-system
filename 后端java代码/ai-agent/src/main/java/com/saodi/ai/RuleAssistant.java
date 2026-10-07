package com.saodi.ai;

import com.saodi.ai.tool.DraftOrderTool;
import com.saodi.ai.tool.FindShowtimesTool;
import com.saodi.ai.tool.ListCinemasTool;
import com.saodi.ai.tool.ListMoviesTool;
import com.saodi.ai.tool.SeatSummaryTool;
import com.saodi.ai.tool.ToolContext;
import com.saodi.ai.vo.AiReply;
import com.saodi.ai.vo.AiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *  没配 api-key 时的兜底：不接模型，直接按关键词判意图、调同一批工具、拼中文回答。
 *  存在的意义是让这套功能在拿到 key 之前就能点通，而不是一个只会报"未配置"的空壳。
 * </p>
 *
 * @author saodi
 */
@Component
public class RuleAssistant {

    @Autowired
    private ListMoviesTool listMoviesTool;
    @Autowired
    private ListCinemasTool listCinemasTool;
    @Autowired
    private FindShowtimesTool findShowtimesTool;
    @Autowired
    private SeatSummaryTool seatSummaryTool;
    @Autowired
    private DraftOrderTool draftOrderTool;

    private static final Pattern COUNT = Pattern.compile("([0-9]+|一|二|两|三|四|五|六)\\s*[个张位人]");

    public AiReply answer(AiRequest request) {
        String message = request.getMessage() == null ? "" : request.getMessage();
        String text = message.toLowerCase();

        AiReply reply = new AiReply();
        reply.setEngine("rule");

        if (contains(text, "下单", "买", "订", "就这个", "确认")) {
            draft(request, reply, count(message));
            if (reply.getDraft() != null) {
                return reply;
            }
        }
        if (contains(text, "座", "位子", "连座", "空位", "靠中间", "几个人")) {
            seats(request, reply, count(message));
            return reply;
        }
        if (contains(text, "场", "几点", "什么时候", "哪场", "排片", "人少", "时间")) {
            showtimes(request, reply);
            return reply;
        }
        if (contains(text, "影院", "影城", "哪家", "哪个店", "地址")) {
            cinemas(reply);
            return reply;
        }
        if (contains(text, "片", "电影", "演什么", "上映", "好看", "推荐")) {
            movies(reply);
            return reply;
        }

        reply.setAnswer("我现在能查：在映影片、有排片的影院、某场几点开始以及还剩多少座、"
                + "帮你挑连座、把订单草稿填好（最后一步仍由你点确认）。"
                + "当前后端没配 ai.api-key，走的是关键词兜底；配好 key 后就能自然对话。");
        return reply;
    }

    private void movies(AiReply reply) {
        Map<String, Object> result = asMap(listMoviesTool.execute(new LinkedHashMap<>()));
        reply.getSteps().add("list_movies");
        reply.setAnswer("在映影片：" + join(rows(result), row -> trim(str(row.get("name")))
                + "（评分 " + orDash(row.get("score")) + "）"));
    }

    private void cinemas(AiReply reply) {
        Map<String, Object> args = new LinkedHashMap<>();
        args.put("onlyWithShowtimes", true);
        Map<String, Object> result = asMap(listCinemasTool.execute(args));
        reply.getSteps().add("list_cinemas");
        reply.setAnswer("只有排过片的影院才点得进去：" + join(rows(result), row -> trim(str(row.get("name")))
                + "（" + orDash(row.get("screenings")) + " 场）")
                + "。库里其余 " + orDash(result.get("withoutShowtimesSkipped")) + " 家一场都没有，别选。");
    }

    private void showtimes(AiRequest request, AiReply reply) {
        Map<String, Object> result = asMap(findShowtimesTool.execute(contextArgs(request)));
        reply.getSteps().add("find_showtimes");
        List<Map<String, Object>> rows = rows(result);
        if (rows.isEmpty()) {
            reply.setAnswer("按现在的条件没查到场次。先告诉我影院和片名，或者在影院页上问我。");
            return;
        }
        Map<String, Object> quietest = quietest(rows);
        StringBuilder answer = new StringBuilder("查到 " + orDash(result.get("total")) + " 场：\n");
        for (Map<String, Object> row : rows.subList(0, Math.min(8, rows.size()))) {
            answer.append("· ").append(line(row)).append('\n');
        }
        answer.append("人最少的是 ").append(line(quietest))
                .append("，场次 id ").append(orDash(quietest.get("id"))).append("。");
        reply.setAnswer(answer.toString());
    }

    private void seats(AiRequest request, AiReply reply, int wanted) {
        Integer showtimeId = contextId(request, "showtimeId");
        if (showtimeId == null) {
            reply.setAnswer("先告诉我场次，或者在某个场次的选座页上问我。");
            return;
        }
        Map<String, Object> args = new LinkedHashMap<>();
        args.put("showtimeId", showtimeId);
        args.put("wantedSeats", wanted);
        Map<String, Object> result = asMap(seatSummaryTool.execute(args));
        reply.getSteps().add("seat_summary");

        Map<String, Object> suggestion = asMap(result.get("suggestion"));
        StringBuilder answer = new StringBuilder();
        answer.append("这一场还剩 ").append(orDash(freeOf(result))).append(" 个空位。");
        if (Boolean.TRUE.equals(suggestion.get("found"))) {
            answer.append("建议坐 ").append(suggestion.get("label")).append("（中间位置）。");
        } else {
            answer.append("凑不齐 ").append(wanted).append(" 个连座：")
                    .append(orDash(suggestion.get("reason"))).append("。");
        }
        reply.setAnswer(answer.toString());
    }

    private void draft(AiRequest request, AiReply reply, int wanted) {
        Integer showtimeId = contextId(request, "showtimeId");
        if (showtimeId == null) {
            return;
        }
        Map<String, Object> summary = asMap(seatSummaryTool.execute(pair("showtimeId", showtimeId,
                "wantedSeats", wanted)));
        Map<String, Object> suggestion = asMap(summary.get("suggestion"));
        if (!Boolean.TRUE.equals(suggestion.get("found"))) {
            reply.setAnswer("这一场凑不齐 " + wanted + " 个连座，先换一场？");
            return;
        }
        Map<String, Object> args = new LinkedHashMap<>();
        args.put("showtimeId", showtimeId);
        args.put("seats", suggestion.get("seats"));
        // 规则兜底也要带上身份，否则这条路径出的草稿没有确认凭证
        Map<String, Object> result = asMap(draftOrderTool.execute(args, new ToolContext(request.getUserId())));
        reply.getSteps().add("draft_order");

        if (!Boolean.TRUE.equals(result.get("ok"))) {
            reply.setAnswer("草稿没生成：" + result.get("problems"));
            return;
        }
        reply.setDraft(result);
        reply.setAnswer("已经选好 " + orDash(result.get("count")) + " 个位子：" + seatLabels(result)
                + "，" + trim(str(result.get("movie"))) + " " + result.get("date") + " " + result.get("time")
                + "，" + trim(str(result.get("cinema"))) + " " + orDash(result.get("hall"))
                + "，合计 " + orDash(result.get("total")) + " 元。点下面的「就按这个下单」过去确认，我不会替你提交。");
    }

    private static String seatLabels(Map<String, Object> draft) {
        List<String> labels = new ArrayList<>();
        for (Map<String, Object> seat : rows(draft, "seats")) {
            labels.add(trim(str(seat.get("label"))));
        }
        return String.join("、", labels);
    }

    private static Map<String, Object> contextArgs(AiRequest request) {
        Map<String, Object> args = new LinkedHashMap<>();
        putIfPresent(args, request, "cinemaId");
        putIfPresent(args, request, "movieId");
        return args;
    }

    private static void putIfPresent(Map<String, Object> args, AiRequest request, String key) {
        Object value = request.getContext() == null ? null : request.getContext().get(key);
        if (value != null && !String.valueOf(value).trim().isEmpty()) {
            args.put(key, value);
        }
    }

    private static Integer contextId(AiRequest request, String key) {
        Object value = request.getContext() == null ? null : request.getContext().get(key);
        if (value == null) {
            return null;
        }
        try {
            return (int) Double.parseDouble(String.valueOf(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static int count(String message) {
        Matcher matcher = COUNT.matcher(message);
        if (!matcher.find()) {
            return 2;
        }
        String raw = matcher.group(1);
        switch (raw) {
            case "一":
                return 1;
            case "二":
                return 2;
            case "两":
                return 2;
            case "三":
                return 3;
            case "四":
                return 4;
            case "五":
                return 5;
            case "六":
                return 6;
            default:
                try {
                    return Math.max(1, Math.min(6, Integer.parseInt(raw)));
                } catch (NumberFormatException e) {
                    return 2;
                }
        }
    }

    private static Map<String, Object> quietest(List<Map<String, Object>> rows) {
        Map<String, Object> best = null;
        double bestRate = Double.MAX_VALUE;
        for (Map<String, Object> row : rows) {
            double seats = num(row.get("seats"));
            if (seats <= 0) {
                continue;
            }
            double rate = num(row.get("sold")) / seats;
            if (rate < bestRate) {
                bestRate = rate;
                best = row;
            }
        }
        return best == null ? rows.get(0) : best;
    }

    private static Object freeOf(Map<String, Object> summary) {
        return showtimeOf(summary).get("free");
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> showtimeOf(Map<String, Object> summary) {
        Object showtime = summary.get("showtime");
        return showtime instanceof Map ? (Map<String, Object>) showtime : new LinkedHashMap<>();
    }

    private static String line(Map<String, Object> row) {
        return trim(str(row.get("movie"))) + " · " + trim(str(row.get("cinema"))) + " "
                + trim(str(row.get("hall"))) + " · " + row.get("date") + " " + row.get("time")
                + " · 空位 " + orDash(row.get("free")) + "/" + orDash(row.get("seats"))
                + " · " + orDash(row.get("price")) + " 元";
    }

    private interface RowText {
        String of(Map<String, Object> row);
    }

    private static String join(List<Map<String, Object>> rows, RowText rowText) {
        List<String> parts = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            parts.add(rowText.of(row));
        }
        return String.join("；", parts);
    }

    @SuppressWarnings("unchecked")
    private static List<Map<String, Object>> rows(Map<String, Object> result) {
        return rows(result, "rows");
    }

    @SuppressWarnings("unchecked")
    private static List<Map<String, Object>> rows(Map<String, Object> result, String key) {
        Object value = result.get(key);
        return value instanceof List ? (List<Map<String, Object>>) value : new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> asMap(Object value) {
        return value instanceof Map ? (Map<String, Object>) value : new LinkedHashMap<>();
    }

    private static Map<String, Object> pair(String k1, Object v1, String k2, Object v2) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    private static boolean contains(String text, String... keys) {
        for (String key : keys) {
            if (text.contains(key)) {
                return true;
            }
        }
        return false;
    }

    private static double num(Object value) {
        return value instanceof Number ? ((Number) value).doubleValue() : 0;
    }

    private static String str(Object value) {
        return value == null ? "" : String.valueOf(value);
    }

    private static String trim(String value) {
        return value == null ? "" : value.trim();
    }

    private static Object orDash(Object value) {
        return value == null || String.valueOf(value).trim().isEmpty() ? "—" : value;
    }
}
