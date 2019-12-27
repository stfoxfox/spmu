package com.company.spmu.web.measure;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Measure;

@UiController("spmu_Measure.edit")
@UiDescriptor("measure-edit.xml")
@EditedEntityContainer("measureDc")
@LoadDataBeforeShow
public class MeasureEdit extends StandardEditor<Measure> {
}