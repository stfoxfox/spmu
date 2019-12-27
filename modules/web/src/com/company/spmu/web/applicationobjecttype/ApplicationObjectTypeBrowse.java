package com.company.spmu.web.applicationobjecttype;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ApplicationObjectType;

@UiController("spmu_ApplicationObjectType.browse")
@UiDescriptor("application-object-type-browse.xml")
@LookupComponent("applicationObjectTypesTable")
@LoadDataBeforeShow
public class ApplicationObjectTypeBrowse extends StandardLookup<ApplicationObjectType> {
}