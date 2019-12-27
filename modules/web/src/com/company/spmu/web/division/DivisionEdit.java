package com.company.spmu.web.division;

import com.company.spmu.web.custom.editors.UuidStandardEditor;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Division;

@UiController("spmu_Division.edit")
@UiDescriptor("division-edit.xml")
@EditedEntityContainer("divisionDc")
@LoadDataBeforeShow
public class DivisionEdit extends UuidStandardEditor<Division> {
}