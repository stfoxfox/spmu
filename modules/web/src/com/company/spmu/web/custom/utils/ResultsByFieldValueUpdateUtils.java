package com.company.spmu.web.custom.utils;

import com.haulmont.cuba.gui.model.CollectionLoader;

import java.util.Map;

public class ResultsByFieldValueUpdateUtils {
    private ResultsByFieldValueUpdateUtils() {
    }

    public static void updateResultsByFilterField(CollectionLoader loader, String name, Object value) {
        ResultsByFieldValueUpdateUtils.setFilterField(loader, name, value);
        loader.load();
    }

    public static void setFilterField(CollectionLoader loader, String name, Object value) {
        if (value != null) {
            loader.setParameter(name, value);
        } else {
            loader.removeParameter(name);
        }
    }

    public static void updateResultsByFilterFields(CollectionLoader loader, Map<String, Object> fields) {
        fields.forEach((name, value) -> {
            ResultsByFieldValueUpdateUtils.setFilterField(loader, name, value);
        });
        loader.load();
    }
}
