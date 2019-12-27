package com.company.spmu.web.applicationobject;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ApplicationObject;

@UiController("spmu_ApplicationObject.browse")
@UiDescriptor("application-object-browse.xml")
@LookupComponent("applicationObjectsTable")
@LoadDataBeforeShow
public class ApplicationObjectBrowse extends StandardLookup<ApplicationObject> {
}