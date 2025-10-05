package io.github.manjago.temperatureapi;

import java.util.Map;
import java.util.stream.Collectors;

final class JsonUtils {

    private JsonUtils() {
    }

    static String fromMap(Map<String, ?> data) {
        if (data == null || data.isEmpty()) {
            return "{}";
        }

        String entries = data.entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .map(entry -> "\"" + escapeString(entry.getKey()) + "\":" + formatValue(entry.getValue()))
                .collect(Collectors.joining(","));

        return "{" + entries + "}";
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        } else if (value instanceof String string) {
            return "\"" + escapeString(string) + "\"";
        } else {
            return value.toString();
        }
    }

    private static String escapeString(String s) {
        if (s == null) {
            return "";
        } else {
            return s.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\b", "\\b")
                    .replace("\f", "\\f")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
        }
    }
}
