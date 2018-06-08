package com.lunatech.airports.utils;
import java.util.Map;

public class ValueUtils {
    public static long getLongValue(Map<String, String> map, String key) {
        return Long.valueOf(map.get(key));
    }

    public static String getStringValue(Map<String, String> map, String key) {
        return map.get(key);
    }
}
