package com.company.spmu.web.screens.applicationstagetype;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ApplicationStageType;

@UiController("spmu_ApplicationStageType.browse")
@UiDescriptor("application-stage-type-browse.xml")
@LookupComponent("applicationStageTypesTable")
@LoadDataBeforeShow
public class ApplicationStageTypeBrowse extends StandardLookup<ApplicationStageType> {
}