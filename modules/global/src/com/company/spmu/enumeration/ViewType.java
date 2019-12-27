package com.company.spmu.enumeration;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ViewType implements EnumClass<String> {

    BASIC("basic"),
    HIERARCHY("hierarchy");

    private String id;

    ViewType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ViewType fromId(String id) {
        for (ViewType at : ViewType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
