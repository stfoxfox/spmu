package com.company.spmu.enumeration;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum PlanVariantStatus implements EnumClass<String> {

    PROJECT("project"),
    REJECTED_RA_WITH_A_NOTE_FOR_LATER_ADJUSTMENT("rejected_ra_with_a_note_for_later_adjustment"),
    APPROVED_RA("approved_ra"),
    DOWNLOAD_IN_YMIP("download_in_ymip"),
    FINALLY_REJECTED_RA("finally_rejected_ra");

    private String id;

    PlanVariantStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PlanVariantStatus fromId(String id) {
        for (PlanVariantStatus at : PlanVariantStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
