package com.company.spmu.enumeration;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ApplicationActive implements EnumClass<String> {

    ACTIVE("Y"),
    ARCHIVE("N"),
    DELETE("D");

    private String id;

    ApplicationActive(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ApplicationActive fromId(String id) {
        for (ApplicationActive at : ApplicationActive.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}