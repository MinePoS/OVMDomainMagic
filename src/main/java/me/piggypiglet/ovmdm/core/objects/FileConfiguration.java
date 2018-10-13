package me.piggypiglet.ovmdm.core.objects;

import com.google.gson.JsonArray;
import com.google.gson.internal.LinkedTreeMap;

import java.util.*;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://piggypiglet.me
// ------------------------------
public final class FileConfiguration {
    private static final List NULL_LIST = new ArrayList<>();
    private static final String NULL_STRING = "null";
    private static final int NULL_NUM = 0;

    private Map<String, Object> itemMap;

    public FileConfiguration(Map<String, Object> itemMap) {
        this.itemMap = itemMap;
    }


    @SuppressWarnings("unchecked")
    public Object get(String path, Object... def) {
        Object object = itemMap.getOrDefault(path, null);

        if (path.contains(".") && !path.startsWith(".") && !path.endsWith(".")) {
            String[] areas = path.split("\\.");
            object = itemMap.getOrDefault(areas[0], null);

            if (areas.length >= 2 && object != null) {
                object = getBuriedObject(areas);
            }
        }

        return object == null ? (def.length >= 1 ? def[0] : null) : object;
    }

    @SuppressWarnings("unchecked")
    public List<String> getStringList(String path, List<String>... def) {
        Object object;

        try {
            object = get(path, (Object[]) def);
        } catch (Exception e) {
            return NULL_LIST;
        }

        if (object instanceof List<?>) {
            for (Object obj : (List<?>) object) {
                if (obj instanceof String) {
                    return (List<String>) object;
                }

                break;
            }
        }

        return def.length >= 1 ? def[0] : NULL_LIST;
    }

    public String getString(String path, String... def) {
        Object object;

        try {
            object = get(path, (Object[]) def);
        } catch (Exception e) {
            return NULL_STRING;
        }

        if (object instanceof String) {
            return (String) object;
        }

        return def.length >= 1 ? def[0] : NULL_STRING;
    }

    public int getInt(String path, Integer... def) {
        Double[] doubleDef = new Double[def.length];
        for (int i = 0; i < doubleDef.length; ++i) doubleDef[i] = (double) def[i];

        return (int) getDouble(path, doubleDef);
    }

    public long getLong(String path, Long... def) {
        Double[] doubleDef = new Double[def.length];
        for (int i = 0; i < doubleDef.length; ++i) doubleDef[i] = (double) def[i];

        return (long) getDouble(path, doubleDef);
    }

    public double getDouble(String path, Double... def) {
        Object object;

        try {
            object = get(path, (Object[]) def);
        } catch (Exception e) {
            return NULL_NUM;
        }

        if (object instanceof Double) {
            return (double) object;
        }

        return def.length >= 1 ? def[0] : NULL_NUM;
    }

    @SuppressWarnings("unchecked")
    private Object getBuriedObject(String[] keys) {
        int i = 1;
        Map<String, Object> endObject = (Map<String, Object>) itemMap.get(keys[0]);

        while (instanceOfMap(endObject.get(keys[i]))) {
            endObject = (Map<String, Object>) endObject.get(keys[i++]);
        }

        return endObject.get(keys[i]);
    }

    private boolean instanceOfMap(Object object) {
        if (object instanceof LinkedTreeMap) {
            Set<?> keys = ((LinkedTreeMap<?, ?>) object).keySet();

            return keys.size() >= 1 && keys.toArray()[0] instanceof String;
        }

        return false;
    }
}