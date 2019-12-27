package com.company.spmu.web.applicationdetailtype;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ApplicationDetailType;

@UiController("spmu_ApplicationDetail.edit")
@UiDescriptor("application-detail-type-edit.xml")
@EditedEntityContainer("applicationDetailTypeDc")
@LoadDataBeforeShow
public class ApplicationDetailTypeEdit extends StandardEditor<ApplicationDetailType> {
}