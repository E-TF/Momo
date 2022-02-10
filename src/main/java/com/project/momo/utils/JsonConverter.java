package com.project.momo.utils;

import org.json.simple.JSONObject;

import java.util.Map;

public class JsonConverter {

    private static final String DEFAULT_KEY = "ERROR";

    public static JSONObject stringToJson(String value) {
        return stringToJson(DEFAULT_KEY, value);
    }

    public static JSONObject stringToJson(String key, String value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, value);

        return jsonObject;
    }

    public static JSONObject mapToJson(Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        map.keySet().forEach(k -> jsonObject.put(k, map.get(k)));

        return jsonObject;
    }

}
