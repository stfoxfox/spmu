package com.company.spmu.web.division;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Division;

@UiController("spmu_Division.browse")
@UiDescriptor("division-browse.xml")
@LookupComponent("divisionsTable")
@LoadDataBeforeShow
public class DivisionBrowse extends StandardLookup<Division> {
}