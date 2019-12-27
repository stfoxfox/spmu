package com.company.spmu.enumeration;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum LinkType implements EnumClass<String> {
    START_START("start_start"),
    START_END("start_end"),
    END_END("end_end")
    ;

    private String id;

    LinkType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static LinkType fromId(String id) {
        for (LinkType at : LinkType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}