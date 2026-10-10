package com.saodi.ai;

import com.saodi.ai.tool.DraftOrderTool;
import com.saodi.ai.tool.FindShowtimesTool;
import com.saodi.ai.tool.HotNowTool;
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
 *  关键词兜底：不调模型，按关键词判意图、调同一批工具、拼中文回答。
 *  存在的意义是让这套功能在拿到 key 之前就能点通，而不是一个只会报"未配置"的空壳。
 *  现在它还是另外两条降级路径的落点：模型方限流/超时，以及一轮里绕满了 max-tool-rounds ——
 *  所以它不是装饰，而是同一批工具的第二个驱动端；两边共用一套座位合法性判断。
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
    @Autowired
    private HotNowTool hotNowTool;

    private static final Pattern COUNT = Pattern.compile("([0-9]+|一|二|两|三|四|五|六)\\s*[个张位人]");

    public AiReply answer(AiRequest request) {
        String message = request.getMessage() == null ? "" : request.getMessage();
        String text = message.toLowerCase();

        AiReply reply = new AiReply();
        reply.setEngine("rule");

        // 「你直接帮我把钱付了」这种要求，规则引擎也得把边界说清楚：草稿可以有，提交那一下永远在用户手里。
        // 这条判断放在下单关键词之前，否则「不用我确认」里的「确认」会先被当成下单意图。
        if (contains(text, "帮我付", "直接付", "不用我确认", "不用确认", "替我确认", "自动支付", "替我提交")) {
            reply.setAnswer("付款和提交订单这一步永远由你在页面上点，我最多把草稿填好；"
                    + "要出草稿就在选座页上问我，或者直接告诉我场次 id。");
            return reply;
        }
        // 「草稿」以前不在关键词里，「重新出一份草稿」会一路掉到最后的帮助文案。
        if (contains(text, "下单", "买", "订", "就这个", "确认", "草稿")) {
            if (draft(request, reply, count(message))) {
                return reply;
            }
            // 想下单但页面里没有场次，也没有别的可查：说清楚缺什么，别一路掉到能力清单。
            reply.setAnswer("下单得先知道是哪一场：在某个场次的选座页上问我，或者直接告诉我场次 id。");
            return reply;
        }
        // 「我们6个人能坐一起吗」里既没有「座」也没有「几个人」，只有一个「坐」——
        // 以前这条关键词一条都不命中，于是回了一段能力清单，用户问的是座位。
        if (contains(text, "座", "坐", "位子", "连座", "连坐", "空位", "靠中间", "几个人", "坐一起", "挨着", "同一排")) {
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
        // 「最近什么片最热」问的是现在，而站内 movie 的想看数停在 2024 年：拿它答就是一份两年前的榜单。
        // 这条得放在影院之后、片之前——再往后会被「片」那个分支抢走，答成站内在映列表。
        if (contains(text, "最近", "热门", "热度", "排行", "榜", "新片", "人气", "在映", "最火")) {
            hot(reply);
            return reply;
        }
        if (contains(text, "片", "电影", "演什么", "上映", "好看", "推荐")) {
            movies(reply);
            return reply;
        }

        // 这句以前写的是「当前后端没配 ai.api-key」，而 key 是配好的：走到这条兜底是模型没应答或者绕满了工具轮次，
        // 原因说错了会让下一个排查的人先去翻配置文件。为什么说这条回答是兜底、具体退的原因看后端日志和 engine 标签。
        reply.setAnswer("我现在能查：外部热度榜（最近什么在映、谁最热）、站内在映影片、有排片的影院、"
                + "某场几点开始以及还剩多少座、帮你挑连座、把订单草稿填好（最后一步仍由你点确认）。"
                + "这一条是关键词兜底答的（engine=rule）：没配模型、模型没应答、或者一轮里绕满了工具次数都会走到这里，问得具体一点我照样能查。");
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

    /**
     * 热度榜走的是同一个 hot_now bean：模型不在的时候，兜底答的也是那份外部快照，
     * 不会一条路径报外部榜、另一条路径报站内 2024 年的想看数。
     */
    private void hot(AiReply reply) {
        Map<String, Object> args = new LinkedHashMap<>();
        args.put("limit", 10);
        Map<String, Object> result = asMap(hotNowTool.execute(args));
        reply.getSteps().add("hot_now");

        if (!Boolean.TRUE.equals(result.get("available"))) {
            reply.setAnswer("外部热度榜这次没通：" + trim(str(result.get("notice")))
                    + " 站内的想看数是 2024 年存的一版，我不拿它冒充现在的热度。"
                    + "要现在能选座的场次，告诉我影院或片名就行。");
            return;
        }

        StringBuilder answer = new StringBuilder("外部热度榜（" + orDash(result.get("source"))
                + "，" + orDash(result.get("fetchedAt")) + " 抓的）：\n");
        for (Map<String, Object> row : rows(result)) {
            answer.append("· ").append(row.get("rank")).append(". ").append(trim(str(row.get("title"))))
                    .append("（评分 ").append(orDash(row.get("rate"))).append("，")
                    .append(onSite(row)).append("）\n");
        }
        answer.append("榜是外部源的名次，能不能在本站买票看后面那句。");
        reply.setAnswer(answer.toString());
    }

    private static String onSite(Map<String, Object> row) {
        Object movieId = row.get("libraryMovieId");
        int count = (int) num(row.get("showtimeCount"));
        if (movieId == null) {
            return "站内没这部";
        }
        return count > 0 ? "站内有 " + count + " 场，影片 id " + movieId : "站内有这部但一场没排";
    }

    private void showtimes(AiRequest request, AiReply reply) {
        Map<String, Object> args = contextArgs(request);
        // 在选座页上问「这场几点开始」，用户指的是页面里那一场。以前这里只带 cinemaId/movieId，
        // 于是回「查到 21 场」加一句「人最少的是」——答非所问，还顺手对没查过的场次下了结论。
        Integer showtimeId = contextId(request, "showtimeId");
        if (showtimeId != null) {
            args.put("showtimeId", showtimeId);
        }
        Map<String, Object> result = asMap(findShowtimesTool.execute(args));
        reply.getSteps().add("find_showtimes");
        List<Map<String, Object>> rows = rows(result);
        if (rows.isEmpty()) {
            reply.setAnswer(str(result.get("notice"))
                    + "按现在的条件没查到场次。先告诉我影院和片名，或者在影院页上问我。");
            return;
        }
        if (showtimeId != null) {
            reply.setAnswer("这一场：" + line(rows.get(0)) + "。");
            return;
        }
        Map<String, Object> quietest = quietest(rows);
        StringBuilder answer = new StringBuilder("查到 " + orDash(result.get("total")) + " 场：\n");
        for (Map<String, Object> row : rows.subList(0, Math.min(8, rows.size()))) {
            answer.append("· ").append(line(row)).append('\n');
        }
        answer.append("人最少的是 ").append(line(quietest))
                .append("，场次 id ").append(orDash(quietest.get("id"))).append("。");
        if (Boolean.TRUE.equals(result.get("truncated"))) {
            answer.append("（清单被截断过，没出现在上面不等于库里没有。）");
        }
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

    /**
     * @return 这一轮是否已经给出了结论。生成草稿、明确说了为什么没生成，都算给了；
     * 只有「页面里根本没有场次」返回 false，让上层继续按其它关键词判意图。
     */
    private boolean draft(AiRequest request, AiReply reply, int wanted) {
        Integer showtimeId = contextId(request, "showtimeId");
        if (showtimeId == null) {
            return false;
        }
        Map<String, Object> summary = asMap(seatSummaryTool.execute(pair("showtimeId", showtimeId,
                "wantedSeats", wanted)));
        Map<String, Object> suggestion = asMap(summary.get("suggestion"));
        if (!Boolean.TRUE.equals(suggestion.get("found"))) {
            reply.setAnswer("这一场凑不齐 " + wanted + " 个连座，先换一场？");
            return true;
        }
        Map<String, Object> args = new LinkedHashMap<>();
        args.put("showtimeId", showtimeId);
        args.put("seats", suggestion.get("seats"));
        // 规则兜底也要带上身份，否则这条路径出的草稿没有确认凭证
        Map<String, Object> result = asMap(draftOrderTool.execute(args, new ToolContext(request.getUserId())));
        reply.getSteps().add("draft_order");

        if (!Boolean.TRUE.equals(result.get("ok"))) {
            reply.setAnswer("草稿没生成：" + result.get("problems"));
            return true;
        }
        reply.setDraft(result);
        reply.setAnswer("已经选好 " + orDash(result.get("count")) + " 个位子：" + seatLabels(result)
                + "，" + trim(str(result.get("movie"))) + " " + result.get("date") + " " + result.get("time")
                + "，" + trim(str(result.get("cinema"))) + " " + orDash(result.get("hall"))
                + "，合计 " + orDash(result.get("total")) + " 元。点下面的「就按这个下单」过去确认，我不会替你提交。");
        return true;
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
