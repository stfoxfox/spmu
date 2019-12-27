package com.company.spmu.web.affilate;

import com.company.spmu.web.custom.editors.UuidStandardEditor;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Affilate;

@UiController("spmu_Affilate.edit")
@UiDescriptor("affilate-edit.xml")
@EditedEntityContainer("affilateDc")
@LoadDataBeforeShow
public class AffilateEdit extends UuidStandardEditor<Affilate> {
}