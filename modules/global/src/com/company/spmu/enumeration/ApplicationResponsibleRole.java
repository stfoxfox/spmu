package com.company.spmu.enumeration;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ApplicationResponsibleRole implements EnumClass<String> {

    INITIATOR("I"),
    EDITOR("E"),
    CURATOR("C"),
    MANAGER("M");

    private String id;

    ApplicationResponsibleRole(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ApplicationResponsibleRole fromId(String id) {
        for (ApplicationResponsibleRole at : ApplicationResponsibleRole.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}