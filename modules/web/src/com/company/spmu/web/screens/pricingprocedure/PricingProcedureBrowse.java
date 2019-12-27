package com.company.spmu.web.screens.pricingprocedure;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.PricingProcedure;

@UiController("spmu_PricingProcedure.browse")
@UiDescriptor("pricing-procedure-browse.xml")
@LookupComponent("pricingProceduresTable")
@LoadDataBeforeShow
public class PricingProcedureBrowse extends StandardLookup<PricingProcedure> {
}