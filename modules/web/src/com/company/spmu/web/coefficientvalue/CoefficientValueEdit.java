package com.company.spmu.web.coefficientvalue;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.CoefficientValue;

@UiController("spmu_CoefficientValue.edit")
@UiDescriptor("coefficient-value-edit.xml")
@EditedEntityContainer("coefficientValueDc")
@LoadDataBeforeShow
public class CoefficientValueEdit extends StandardEditor<CoefficientValue> {
}