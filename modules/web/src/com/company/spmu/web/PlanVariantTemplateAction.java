package com.company.spmu.web;

import com.company.spmu.entity.PlanVariantTemplate;
import com.haulmont.cuba.gui.screen.StandardCloseAction;

public class PlanVariantTemplateAction extends StandardCloseAction {
    public Boolean isNew;
    public PlanVariantTemplate template;
    public String name;

    public PlanVariantTemplateAction(Boolean isNew, String name, PlanVariantTemplate template) {
        super("PlanVariantTemplate");
        this.isNew = isNew;
        this.template = template;
        this.name = name;
    }
}
