package io.github.manjago.devicemanagementservice.util;

import com.google.gson.Gson;

public class JsonUtil {
    private static final Gson GSON = new Gson();

    private JsonUtil() {
        // приватный конструктор, чтобы не создавали экземпляры
    }

    public static Gson getGson() {
        return GSON;
    }

    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }
}