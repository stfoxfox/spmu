package com.company.spmu.web.flyghttype;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.FlyghtType;

@UiController("spmu_FlyghtType.edit")
@UiDescriptor("flyght-type-edit.xml")
@EditedEntityContainer("flyghtTypeDc")
@LoadDataBeforeShow
public class FlyghtTypeEdit extends StandardEditor<FlyghtType> {
}