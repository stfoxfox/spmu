package com.company.spmu.web.coefficient;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Coefficient;

@UiController("spmu_Coefficient.browse")
@UiDescriptor("coefficient-browse.xml")
@LookupComponent("coefficientsTable")
@LoadDataBeforeShow
public class CoefficientBrowse extends StandardLookup<Coefficient> {
}