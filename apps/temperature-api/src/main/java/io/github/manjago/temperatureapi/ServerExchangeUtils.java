package io.github.manjago.temperatureapi;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import static io.github.manjago.temperatureapi.JsonUtils.fromMap;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

final class ServerExchangeUtils {

    private ServerExchangeUtils() {
    }

    static void sendJsonResponse(HttpExchange t, int code, Map<String, ?> jsonMap) throws IOException {
        t.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        sendResponse(t, code, fromMap(jsonMap));
    }

    final static void sendProblemJsonResponse(HttpExchange t, int status, String title, String detail) throws IOException {
        final Map<String, Object> problemDetails = Map.of(
                "type", "about:blank",
                "title", title,
                "status", status,
                "detail", detail,
                "instance", t.getRequestURI().getPath()
        );

        t.getResponseHeaders().set("Content-Type", "application/problem+json; charset=utf-8");
        sendResponse(t, status, fromMap(problemDetails));
    }

    static Map<String, String> parseQueryString(String query) {
        if (query == null || query.isEmpty()) {
            return Map.of();
        }
        return Arrays.stream(query.split("&"))
                .map(s -> s.split("=", 2))
                .filter(kv -> kv.length == 2)
                .collect(Collectors.toMap(kv -> URLDecoder.decode(kv[0], StandardCharsets.UTF_8),
                        kv -> URLDecoder.decode(kv[1], StandardCharsets.UTF_8))
                );
    }

    static String extractLastSegment(String path) {
        if (path == null || path.isBlank()) {
            return null;
        }

        final int lastSlash = path.lastIndexOf('/');
        if (lastSlash == -1) {
            return path; // нет слэшей
        }
        if (lastSlash == path.length() - 1) {
            int prevSlash = path.lastIndexOf('/', lastSlash - 1);
            return prevSlash == -1
                    ? path.substring(0, lastSlash)
                    : path.substring(prevSlash + 1, lastSlash);
        }
        return path.substring(lastSlash + 1);
    }

    private static void sendResponse(HttpExchange t, int code, String body) throws IOException {
        addCommonHeaders(t);
        final byte[] responseBytes = body.getBytes(StandardCharsets.UTF_8);
        t.sendResponseHeaders(code, responseBytes.length);
        try (OutputStream os = t.getResponseBody()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }
    }

    private static void addCommonHeaders(HttpExchange t) {
        final Headers headers = t.getResponseHeaders();

        headers.set("Server", "MyAwesomeTemperatureAPI/1.0");
        headers.set("Date", DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC)));
        headers.set("X-Content-Type-Options", "nosniff");
        headers.set("X-Frame-Options", "DENY");
        headers.set("Content-Security-Policy", "default-src 'none'");
    }

}
