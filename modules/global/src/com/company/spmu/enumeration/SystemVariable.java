package com.company.spmu.enumeration;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum SystemVariable implements EnumClass<String> {

    CURRENT_TIME("current_time"),
    CURRENT_YEAR("current_year"),
    CURRENT_DATE("current_date"),
    COUNT_PAGE("count_page"),
    NUMBER_PAGE("number_page"),
    NUMBER_STRING("number_string"),
    FULL_NAME("full_name"),
    TRUE("true"),
    FALSE("false"),
    EMPTY("empty");

    private String id;

    SystemVariable(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static SystemVariable fromId(String id) {
        for (SystemVariable at : SystemVariable.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}