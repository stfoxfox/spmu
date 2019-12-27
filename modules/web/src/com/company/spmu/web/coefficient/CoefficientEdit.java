package com.company.spmu.web.coefficient;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Coefficient;

@UiController("spmu_Coefficient.edit")
@UiDescriptor("coefficient-edit.xml")
@EditedEntityContainer("coefficientDc")
@LoadDataBeforeShow
public class CoefficientEdit extends StandardEditor<Coefficient> {
}