package com.company.spmu.web.applicationobjecttype;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ApplicationObjectType;

@UiController("spmu_ApplicationObjectType.edit")
@UiDescriptor("application-object-type-edit.xml")
@EditedEntityContainer("applicationObjectTypeDc")
@LoadDataBeforeShow
public class ApplicationObjectTypeEdit extends StandardEditor<ApplicationObjectType> {
}