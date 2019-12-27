package com.company.spmu.web.flyghttype;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.FlyghtType;

@UiController("spmu_FlyghtType.browse")
@UiDescriptor("flyght-type-browse.xml")
@LookupComponent("flyghtTypesTable")
@LoadDataBeforeShow
public class FlyghtTypeBrowse extends StandardLookup<FlyghtType> {
}