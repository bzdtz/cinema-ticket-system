package com.saodi.util;

/**
 * <p>
 *  片名比对用的归一化。热度榜（外部源）和 movie.name（站内）要对得上，
 *  而站内有些片名带着字面量的 "\n" 前缀（movie.id 45、47 就是这样）和尾随空格，
 *  豆瓣给的是干净名字，直接 equals 会把站内明明在售的片判成「没这部」。
 * </p>
 *
 * @author saodi
 */
public final class Titles {

    private Titles() {
    }

    /** 去掉空白、书名号、引号和历史脏前缀，只用于比对，不用于展示 */
    public static String normalize(String title) {
        if (title == null) {
            return "";
        }
        return title.replace("\\n", "").replace("《", "").replace("》", "")
                .replace("“", "").replace("”", "").replace("\"", "")
                .replaceAll("\\s+", "").toLowerCase();
    }
}
