package com.company.spmu.web.screens.pricingprocedure;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.PricingProcedure;

@UiController("spmu_PricingProcedure.edit")
@UiDescriptor("pricing-procedure-edit.xml")
@EditedEntityContainer("pricingProcedureDc")
@LoadDataBeforeShow
public class PricingProcedureEdit extends StandardEditor<PricingProcedure> {
}