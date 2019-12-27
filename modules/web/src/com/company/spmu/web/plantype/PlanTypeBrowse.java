package com.company.spmu.web.plantype;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.PlanType;

@UiController("spmu_PlanType.browse")
@UiDescriptor("plan-type-browse.xml")
@LookupComponent("planTypesTable")
@LoadDataBeforeShow
public class PlanTypeBrowse extends StandardLookup<PlanType> {
}