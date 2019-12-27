package com.company.spmu.enumeration;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;

/**
 * Виды заявок
 */
public enum ApplicationType implements EnumClass<String> {

    INCOME("i"),
    EVENT("e"),
    EXPENSE("ex");

    private String id;

    ApplicationType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ApplicationType fromId(String id) {
        for (ApplicationType at : ApplicationType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}