package com.company.spmu.enumeration;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum AggregateFunction implements EnumClass<String> {

    ;

    private String id;

    AggregateFunction(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static AggregateFunction fromId(String id) {
        for (AggregateFunction at : AggregateFunction.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}