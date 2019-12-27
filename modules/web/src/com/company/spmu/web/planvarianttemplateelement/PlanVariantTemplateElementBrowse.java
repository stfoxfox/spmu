package com.company.spmu.web.planvarianttemplateelement;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.PlanVariantTemplateElement;

@UiController("spmu_PlanVariantTemplateElement.browse")
@UiDescriptor("plan-variant-template-element-browse.xml")
@LookupComponent("planVariantTemplateElementsTable")
@LoadDataBeforeShow
public class PlanVariantTemplateElementBrowse extends StandardLookup<PlanVariantTemplateElement> {
}
