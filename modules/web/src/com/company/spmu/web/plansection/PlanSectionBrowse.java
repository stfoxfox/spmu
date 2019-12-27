package com.company.spmu.web.plansection;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.PlanSection;

@UiController("spmu_PlanSection.browse")
@UiDescriptor("plan-section-browse.xml")
@LookupComponent("planSectionsTable")
@LoadDataBeforeShow
public class PlanSectionBrowse extends StandardLookup<PlanSection> {
}