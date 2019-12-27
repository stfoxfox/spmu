package com.company.spmu.web.applicationstage;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ApplicationStage;

@UiController("spmu_ApplicationStage.lookup")
@UiDescriptor("application-stage-lookup.xml")
@LookupComponent("table")
@LoadDataBeforeShow
@DialogMode(forceDialog = true, resizable = true, closeOnClickOutside = true)
public class ApplicationStageLookup extends StandardLookup<ApplicationStage> {
}