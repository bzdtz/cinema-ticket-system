package com.saodi.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 座位矩阵（showtimes.seat / hall.hall_size 存的那个字符串）的唯一解析入口。
 *
 * 库里历史写法有两种：Arrays.toString 出来的带空格 "[[0, 1, -1]]"，JSON.stringify 出来的不带。
 * 所以计数只按数字切、不依赖分隔符；需要行列结构时才走 parse()。
 */
public final class SeatMatrix {

    /** 0 可选，1 已售，-1 无座位（过道），-2 损坏 */
    public static final int VALUE_FREE = 0;
    public static final int VALUE_SOLD = 1;
    public static final int VALUE_NO_SEAT = -1;
    public static final int VALUE_DAMAGED = -2;
    /** 库里存了根本不是数字的内容（见过 "p30"，前端类名漏进了数据库） */
    public static final int VALUE_UNKNOWN = Integer.MIN_VALUE;

    /** count() 的返回下标 */
    public static final int FREE = 0;
    public static final int SOLD = 1;
    public static final int NO_SEAT = 2;
    public static final int DAMAGED = 3;
    public static final int UNKNOWN = 4;

    private SeatMatrix() {
    }

    /**
     * 按数字逐个统计，不要求字符串结构合法。下标见本类的 FREE..UNKNOWN 常量。
     */
    public static int[] count(String seat) {
        int[] counts = new int[5];
        if (seat == null || seat.trim().isEmpty()) {
            return counts;
        }
        for (String token : seat.replaceAll("[^-0-9]", " ").trim().split("\\s+")) {
            int value;
            try {
                value = Integer.parseInt(token);
            } catch (NumberFormatException e) {
                counts[UNKNOWN]++;
                continue;
            }
            if (value == VALUE_FREE) {
                counts[FREE]++;
            } else if (value == VALUE_SOLD) {
                counts[SOLD]++;
            } else if (value == VALUE_NO_SEAT) {
                counts[NO_SEAT]++;
            } else if (value == VALUE_DAMAGED) {
                counts[DAMAGED]++;
            } else {
                counts[UNKNOWN]++;
            }
        }
        return counts;
    }

    /**
     * 解析成行列网格，越界或结构异常时返回空列表而不抛异常。
     *
     * 只按 "],[" 切行：早先用「逗号」切会把行内的单元格也拆开，
     * 13 排 10 列的矩阵会被拆成 124 个「一行一格」，连座和下单校验就全废了。
     */
    public static List<List<Integer>> parse(String seat) {
        if (seat == null || seat.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String body = seat.trim();
        if (!body.startsWith("[") || !body.endsWith("]")) {
            return Collections.emptyList();
        }
        body = body.substring(1, body.length() - 1).trim();
        if (body.isEmpty()) {
            return Collections.emptyList();
        }
        List<List<Integer>> grid = new ArrayList<>();
        for (String rowText : body.split("\\]\\s*,\\s*\\[")) {
            String cells = rowText.replace("[", "").replace("]", "").trim();
            List<Integer> row = new ArrayList<>();
            if (!cells.isEmpty()) {
                for (String token : cells.split("\\s*,\\s*")) {
                    row.add(parseInt(token));
                }
            }
            grid.add(row);
        }
        return grid;
    }

    /** 只有确切的 0 才可售；-2 损坏、-1 过道、以及库里残留的非法取值都不可售。 */
    public static boolean isSellable(Integer value) {
        return value != null && value == VALUE_FREE;
    }

    /**
     * 写回库里用的紧凑形式，和 parse() 同源：行内逗号分隔、行间 "],["。
     * 服务端改完矩阵必须走这里序列化，不能拿前端传来的字符串直接覆盖。
     */
    public static String serialize(List<List<Integer>> grid) {
        StringBuilder text = new StringBuilder("[");
        for (int r = 0; r < grid.size(); r++) {
            if (r > 0) {
                text.append(',');
            }
            text.append('[');
            List<Integer> row = grid.get(r);
            for (int c = 0; c < row.size(); c++) {
                if (c > 0) {
                    text.append(',');
                }
                text.append(row.get(c));
            }
            text.append(']');
        }
        return text.append(']').toString();
    }

    private static int parseInt(String token) {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            // 认不出来的取值交给 isSellable 判成不可售，和前端渲染器的处理保持一致
            return VALUE_UNKNOWN;
        }
    }
}
