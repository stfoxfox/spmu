package com.company.spmu.web.coefficientvalue;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.CoefficientValue;

@UiController("spmu_CoefficientValue.browse")
@UiDescriptor("coefficient-value-browse.xml")
@LookupComponent("coefficientValuesTable")
@LoadDataBeforeShow
public class CoefficientValueBrowse extends StandardLookup<CoefficientValue> {
}