package com.vansisto.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;

public class RestUtil {
    private static Gson gson = new Gson();

    public static <T> T getFromJson(HttpServletRequest req, Class<T> clazz) {
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gson.fromJson(sb.toString(), clazz);
    }

    public static long getIdFromJson(HttpServletRequest req) {
        try {
            BufferedReader reader = req.getReader();
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            String json = jsonBuilder.toString();

            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            if (jsonObject.has("id")) {
                return jsonObject.get("id").getAsLong();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1; // Возвращаем -1, если "id" не найден или возникли ошибки при чтении JSON
    }

    public static <T> String toJson(T t) {
        return gson.toJson(t);
    }
}

