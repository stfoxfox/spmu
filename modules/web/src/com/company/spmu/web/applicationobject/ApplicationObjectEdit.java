package com.company.spmu.web.applicationobject;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ApplicationObject;

@UiController("spmu_ApplicationObject.edit")
@UiDescriptor("application-object-edit.xml")
@EditedEntityContainer("applicationObjectDc")
@LoadDataBeforeShow
public class ApplicationObjectEdit extends StandardEditor<ApplicationObject> {
}