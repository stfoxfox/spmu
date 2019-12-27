package com.company.spmu.web.target;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Target;

@UiController("spmu_Target.edit")
@UiDescriptor("target-edit.xml")
@EditedEntityContainer("targetDc")
@LoadDataBeforeShow
public class TargetEdit extends StandardEditor<Target> {
}