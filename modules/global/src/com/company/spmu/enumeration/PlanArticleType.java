package com.company.spmu.enumeration;

import com.haulmont.chile.core.datatypes.impl.EnumClass;
import org.apache.commons.lang3.StringUtils;


public enum PlanArticleType implements EnumClass<String> {

    CODE_20120("20120"),
    CODE_20200("20200"),
    CODE_20301("20301"),
    CODE_20302("20302"),
    CODE_20303("20303"),
    CODE_20304("20304"),
    CODE_20305("20305"),
    CODE_20306("20306"),
    CODE_20307("20307"),
    CODE_20308("20308"),
    CODE_20309("20309"),
    CODE_20310("20310"),
    CODE_20401("20401"),
    CODE_20402("20402"),
    CODE_20403("20403"),
    CODE_20404("20404"),
    CODE_20405("20405"),
    CODE_20406("20406"),
    CODE_20407("20407"),
    CODE_20408("20408"),
    CODE_20500("20500"),
    DEFAULT(null);

    private String id;

    PlanArticleType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    public static PlanArticleType fromId(String id) {
        for (PlanArticleType at : PlanArticleType.values()) {
            if (StringUtils.equals(at.getId(), id)) {
                return at;
            }
        }
        return DEFAULT;
    }
}