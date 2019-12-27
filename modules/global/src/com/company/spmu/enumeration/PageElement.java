package com.company.spmu.enumeration;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum PageElement implements EnumClass<String> {

    TITLE("title"),
    REPORT_END("report_end"),
    PAGE_HEADER("page_header"),
    PAGE_FOOTER("page_footer");

    private String id;

    PageElement(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PageElement fromId(String id) {
        for (PageElement at : PageElement.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}