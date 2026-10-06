package com.saodi.ai.tool;

import com.saodi.po.Showtimes;
import com.saodi.util.SeatMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  工具：生成下单草稿。刻意不写库——智能体只把影院/影片/场次/座位选好并校验一遍，
 *  最后一步"确认下单"必须留在人手里（座位矩阵服务端没有二次校验，自动下单很容易锁错）。
 * </p>
 *
 * @author saodi
 */
@Component
public class DraftOrderTool implements AgentTool {

    @Autowired
    private ShowtimeReader reader;

    @Override
    public String name() {
        return "draft_order";
    }

    @Override
    public String description() {
        return "生成一份下单草稿：校验座位确实可选，算好总价，交给前端预填。这个工具不会真的下单，"
                + "用户必须自己在页面上点确认。seats 是 [[行,列],...]，行列都从 0 开始。"
                + "任何一个位子不可用时整单不生成，会返回具体原因。";
    }

    @Override
    public Map<String, Object> parameters() {
        return Schemas.object(
                Schemas.props(
                        "showtimeId", Schemas.prop("integer", "场次 id，必填"),
                        "seats", Schemas.arrayOf(
                                Schemas.prop("array", "单个座位：[行, 列]，0 下标"),
                                "要订的座位列表")),
                "showtimeId", "seats");
    }

    @Override
    public Object execute(Map<String, Object> args) {
        Integer showtimeId = Args.id(args, "showtimeId");
        Showtimes showtimes = reader.find(showtimeId);
        if (showtimes == null) {
            throw new IllegalArgumentException("场次 #" + showtimeId + " 不存在");
        }
        List<List<Integer>> grid = SeatMatrix.parse(showtimes.getSeat());
        if (grid.isEmpty()) {
            throw new IllegalArgumentException("场次 #" + showtimeId + " 的座位数据无法解析");
        }

        List<Object> requested = Args.list(args.get("seats"));
        if (requested.isEmpty()) {
            throw new IllegalArgumentException("seats 不能为空");
        }

        Set<String> seen = new LinkedHashSet<>();
        List<Map<String, Object>> seats = new ArrayList<>();
        List<String> problems = new ArrayList<>();

        for (Object item : requested) {
            List<Object> cell = Args.list(item);
            if (cell.size() < 2) {
                problems.add("座位格式应该是 [行, 列]，收到 " + cell);
                continue;
            }
            Integer row = Args.integer(cell.get(0));
            Integer col = Args.integer(cell.get(1));
            if (row == null || col == null || row < 0 || row >= grid.size()
                    || col < 0 || col >= grid.get(row).size()) {
                problems.add(describe(row, col) + " 超出这个影厅的范围");
                continue;
            }
            Integer value = grid.get(row).get(col);
            if (!SeatMatrix.isSellable(value)) {
                problems.add(describe(row, col) + " " + reason(value));
                continue;
            }
            String key = row + ":" + col;
            if (!seen.add(key)) {
                problems.add(describe(row, col) + " 重复选了");
                continue;
            }
            Map<String, Object> seat = new LinkedHashMap<>();
            seat.put("row", row);
            seat.put("col", col);
            seat.put("label", describe(row, col));
            seats.add(seat);
        }

        Map<String, Object> result = new LinkedHashMap<>();
        if (!problems.isEmpty()) {
            result.put("ok", false);
            result.put("problems", problems);
            result.put("hint", "换一个场次或者去掉这些位子再试");
            return result;
        }

        BigDecimal unit = showtimes.getSale() == null ? BigDecimal.ZERO : showtimes.getSale();
        Map<String, Object> showtime = reader.describe(showtimes);
        result.put("ok", true);
        result.put("placed", false);
        result.put("requiresUserConfirmation", true);
        result.put("showtimeId", showtimes.getId());
        result.put("movie", showtime.get("movie"));
        result.put("cinema", showtime.get("cinema"));
        result.put("hall", showtime.get("hall"));
        result.put("date", showtime.get("date"));
        result.put("time", showtime.get("time"));
        result.put("seats", seats);
        result.put("count", seats.size());
        result.put("unitPrice", unit);
        result.put("total", unit.multiply(new BigDecimal(seats.size())));
        return result;
    }

    private static String describe(Integer row, Integer col) {
        return (row == null || col == null) ? "（坐标缺失）" : (row + 1) + "排" + (col + 1) + "座";
    }

    private static String reason(Integer value) {
        if (value == null) {
            return "这一格没有数据";
        }
        if (value == SeatMatrix.VALUE_SOLD) {
            return "已经卖出去了";
        }
        if (value == SeatMatrix.VALUE_NO_SEAT) {
            return "不是座位（过道）";
        }
        if (value == SeatMatrix.VALUE_DAMAGED) {
            return "是损坏座位";
        }
        if (value == SeatMatrix.VALUE_UNKNOWN) {
            return "库里存的内容不是座位取值，按不可售处理";
        }
        return "取值异常（" + value + "），按不可售处理";
    }
}
