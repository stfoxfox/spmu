package com.company.spmu.web.finsource;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Finsource;

@UiController("spmu_Finsource.edit")
@UiDescriptor("finsource-edit.xml")
@EditedEntityContainer("finsourceDc")
@LoadDataBeforeShow
public class FinsourceEdit extends StandardEditor<Finsource> {
}