package com.saodi.ai.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  模型传来的参数是 JSON 反序列化后的 Map，数字可能是 Integer / Long / Double / String，
 *  这里统一收口，免得每个工具各写一遍 instanceof。
 * </p>
 *
 * @author saodi
 */
public final class Args {

    private Args() {
    }

    public static String text(Object value) {
        return value == null ? "" : String.valueOf(value).trim();
    }

    public static Integer integer(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        String text = String.valueOf(value).trim();
        if (text.isEmpty()) {
            return null;
        }
        try {
            return (int) Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static int integer(Object value, int fallback) {
        Integer parsed = integer(value);
        return parsed == null ? fallback : parsed;
    }

    public static int clamp(int value, int low, int high) {
        return Math.min(Math.max(value, low), high);
    }

    @SuppressWarnings("unchecked")
    public static List<Object> list(Object value) {
        List<Object> result = new ArrayList<>();
        if (value instanceof List) {
            result.addAll((List<Object>) value);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> map(Object value) {
        return value instanceof Map ? (Map<String, Object>) value : null;
    }

    public static Integer id(Map<String, Object> args, String key) {
        return integer(args.get(key));
    }
}
