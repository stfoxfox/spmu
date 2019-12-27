package com.company.spmu.enumeration;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum Calendar implements EnumClass<String> {

    WORKING("W"),
    CALENDAR("C");

    private String id;

    Calendar(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Calendar fromId(String id) {
        for (Calendar at : Calendar.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}