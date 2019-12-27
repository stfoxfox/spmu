package com.company.spmu.web.applicationdetailtype;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ApplicationDetailType;

@UiController("spmu_ApplicationDetail.browse")
@UiDescriptor("application-detail-type-browse.xml")
@LookupComponent("applicationDetailTypesTable")
@LoadDataBeforeShow
public class ApplicationDetailTypeBrowse extends StandardLookup<ApplicationDetailType> {
}