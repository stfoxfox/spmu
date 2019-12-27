package com.company.spmu.web;

import com.haulmont.cuba.gui.screen.StandardCloseAction;

public class PlanVarianTemplateElementCloseAction extends StandardCloseAction {
    public String attribute;
    public String varValue;

    public PlanVarianTemplateElementCloseAction(String attribute, String varValue) {
        super("PlanVarianTemplateElementCloseAction");
        this.attribute = attribute;
        this.varValue = varValue;
    }

}
