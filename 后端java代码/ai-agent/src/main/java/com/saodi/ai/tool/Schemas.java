package com.saodi.ai.tool;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  拼 JSON Schema 的小工具，省掉每个工具都手写一遍 LinkedHashMap。
 * </p>
 *
 * @author saodi
 */
public final class Schemas {

    private Schemas() {
    }

    public static Map<String, Object> object(Map<String, Object> properties, String... required) {
        Map<String, Object> schema = new LinkedHashMap<>();
        schema.put("type", "object");
        schema.put("properties", properties);
        if (required.length > 0) {
            schema.put("required", Arrays.asList(required));
        }
        return schema;
    }

    public static Map<String, Object> prop(String type, String description) {
        Map<String, Object> property = new LinkedHashMap<>();
        property.put("type", type);
        property.put("description", description);
        return property;
    }

    public static Map<String, Object> arrayOf(Map<String, Object> item, String description) {
        Map<String, Object> property = new LinkedHashMap<>();
        property.put("type", "array");
        property.put("items", item);
        property.put("description", description);
        return property;
    }

    public static Map<String, Object> props(Object... keyValues) {
        Map<String, Object> properties = new LinkedHashMap<>();
        for (int i = 0; i + 1 < keyValues.length; i += 2) {
            properties.put(String.valueOf(keyValues[i]), keyValues[i + 1]);
        }
        return properties;
    }

    @SuppressWarnings("unchecked")
    public static List<Object> list(Map<String, Object> property, String key) {
        Object value = property.get(key);
        return value instanceof List ? (List<Object>) value : null;
    }
}
