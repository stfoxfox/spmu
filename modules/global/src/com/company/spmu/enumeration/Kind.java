package com.company.spmu.enumeration;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;

/**
 * Виды расходов/доходов
 */
public enum Kind implements EnumClass<String> {

    CENTRALIZED("С"),
    DECENTRALIZED("D"),
    GENERAL("U");

    private String id;

    Kind(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Kind fromId(String id) {
        for (Kind at : Kind.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}