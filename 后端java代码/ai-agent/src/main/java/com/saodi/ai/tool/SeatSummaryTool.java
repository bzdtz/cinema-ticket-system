package com.saodi.ai.tool;

import com.saodi.po.Showtimes;
import com.saodi.util.SeatMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  工具：把一场的座位矩阵压成模型看得懂、又不会撑爆上下文的摘要，并给出连座推荐。
 *  矩阵本身绝不回给模型——一个 200 格的矩阵比整段对话都长。
 * </p>
 *
 * @author saodi
 */
@Component
public class SeatSummaryTool implements AgentTool {

    @Autowired
    private ShowtimeReader reader;

    @Override
    public String name() {
        return "seat_summary";
    }

    @Override
    public String description() {
        return "看某一场具体还剩哪些位子。返回总览、有空位的排及其连续空段，"
                + "以及 wantedSeats 个连座的推荐（优先中间排、中间列）。座位下标从 0 开始，展示给用户要 +1。";
    }

    @Override
    public Map<String, Object> parameters() {
        return Schemas.object(
                Schemas.props(
                        "showtimeId", Schemas.prop("integer", "场次 id，必填"),
                        "wantedSeats", Schemas.prop("integer", "想要几个连座，默认 2，最多 6")),
                "showtimeId");
    }

    @Override
    public Object execute(Map<String, Object> args) {
        Integer showtimeId = Args.id(args, "showtimeId");
        int wanted = Args.clamp(Args.integer(args.get("wantedSeats"), 2), 1, 6);

        Showtimes showtimes = reader.find(showtimeId);
        if (showtimes == null) {
            throw new IllegalArgumentException("场次 #" + showtimeId + " 不存在");
        }
        List<List<Integer>> grid = SeatMatrix.parse(showtimes.getSeat());
        if (grid.isEmpty()) {
            throw new IllegalArgumentException("场次 #" + showtimeId + " 的座位数据无法解析");
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("showtime", reader.describe(showtimes));
        result.put("rows", rowSummary(grid));
        result.put("suggestion", suggest(grid, wanted));
        return result;
    }

    private static List<Map<String, Object>> rowSummary(List<List<Integer>> grid) {
        List<Map<String, Object>> rows = new ArrayList<>();
        for (int r = 0; r < grid.size(); r++) {
            List<String> runs = new ArrayList<>();
            int free = 0;
            int start = -1;
            List<Integer> row = grid.get(r);
            for (int c = 0; c <= row.size(); c++) {
                boolean sellable = c < row.size() && SeatMatrix.isSellable(row.get(c));
                if (sellable) {
                    if (start < 0) {
                        start = c;
                    }
                    free++;
                } else if (start >= 0) {
                    runs.add((start + 1) + "-" + c);
                    start = -1;
                }
            }
            if (free > 0) {
                Map<String, Object> summary = new LinkedHashMap<>();
                summary.put("row", r + 1);
                summary.put("free", free);
                summary.put("freeRuns", runs);
                rows.add(summary);
            }
        }
        return rows;
    }

    /**
     * 在所有长度为 wanted 的连续可售窗口里，挑几何中心最靠近影厅中心的那个。
     * 返回的 row/col 是 0 下标，label 才是给人看的第几排几座。
     */
    static Map<String, Object> suggest(List<List<Integer>> grid, int wanted) {
        double midRow = (grid.size() - 1) / 2.0;
        Map<String, Object> best = null;
        double bestScore = Double.NEGATIVE_INFINITY;

        for (int r = 0; r < grid.size(); r++) {
            List<Integer> row = grid.get(r);
            double midCol = (row.size() - 1) / 2.0;
            for (int c = 0; c + wanted <= row.size(); c++) {
                boolean sellable = true;
                for (int k = 0; k < wanted; k++) {
                    if (!SeatMatrix.isSellable(row.get(c + k))) {
                        sellable = false;
                        break;
                    }
                }
                if (!sellable) {
                    continue;
                }
                double centerCol = c + (wanted - 1) / 2.0;
                double score = -Math.abs(r - midRow) * 4 - Math.abs(centerCol - midCol) * 3;
                if (score > bestScore) {
                    bestScore = score;
                    best = window(r, c, wanted);
                }
            }
        }

        if (best == null) {
            Map<String, Object> none = new LinkedHashMap<>();
            none.put("found", false);
            none.put("reason", "没有 " + wanted + " 个连续空位了，可以拆开坐或者换一场");
            return none;
        }
        best.put("found", true);
        return best;
    }

    private static Map<String, Object> window(int row, int startCol, int wanted) {
        List<List<Integer>> seats = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        for (int k = 0; k < wanted; k++) {
            List<Integer> cell = new ArrayList<>();
            cell.add(row);
            cell.add(startCol + k);
            seats.add(cell);
            labels.add((row + 1) + "排" + (startCol + k + 1) + "座");
        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("seats", seats);
        result.put("label", String.join("、", labels));
        return result;
    }
}
