package com.company.spmu.web.mandate;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Mandate;

@UiController("spmu_Mandate.edit")
@UiDescriptor("mandate-edit.xml")
@EditedEntityContainer("mandateDc")
@LoadDataBeforeShow
public class MandateEdit extends StandardEditor<Mandate> {
}