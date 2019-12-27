package com.company.spmu.enumeration;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ApplicationStatus implements EnumClass<String> {

    NEW("new"),
    REVIEW("review"),
    APPROVAL("approval"),
    ARCHIVE("archive"),
    CANCELED("canceled"),
    PROJECT("project"),
    AFFILIATE_ON_APPROVAL("affiliate_on_approval"),
    GENERAL_MANAGEMENT_ON_APPROVAL("general_management_on_approval"),
    AFFILIATE_APPROVED("affiliate_approved"),
    GENERAL_MANAGEMENT_APPROVED("general_management_approved"),
    REGISTRY_APPROVED("registry_approved"),
    INCLUDED_INTO_APPROVED_PLAN("included_into_approved_plan"),
    DONE("done"),
    OBTAINED_FROM_YIMVNUT("obtained_from_yimvnyt"),
    FINALLY_REJECTED_IN_THE_GENERAL_MANAGEMENT("finally_rejected_in_the_general_management"),
    REJECTED_GENERAL_MANAGEMENT_WITH_A_NOTE_FOR_LATER_ADJUSTMENT("rejected_general_management_with_a_note_for_later_adjustment"),
    INCLUDED_IN_THE_GENERAL_MANAGEMENT_OPTION("included_in_the_general_management_option"),
    ON_APPROVAL_RA("on_approval_ra "),
    FINALLY_REJECTED_RA("finally_rejected_ra"),
    REJECTED_RA_WITH_A_NOTE_FOR_LATER_ADJUSTMENT("rejected_ra_with_a_note_for_later_adjustment"),
    INCLUDED_IN_THE_RA_OPTION("included_in_the_ra_option"),
    APPROVED_RA("approved_ra"),
    EVENT_CREATED("event_created"),
    EVENT_CHANGED("event_changed"),
    EVENT_CLOSED("event_closed"),
    ON_AFFIRMATION_GENERAL_MANAGEMENT("on_affirmation_general_management"),
    AFFIRMATION_GENERAL_MANAGEMENT("affirmation_general_management"),
    DOWNLOAD_IN_YMIP("download_in_ymip"),
    ARCHIVAL("archival");

    private String id;

    ApplicationStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ApplicationStatus fromId(String id) {
        for (ApplicationStatus at : ApplicationStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
