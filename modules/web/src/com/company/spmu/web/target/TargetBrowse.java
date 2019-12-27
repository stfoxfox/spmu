package com.company.spmu.web.target;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Target;

@UiController("spmu_Target.browse")
@UiDescriptor("target-browse.xml")
@LookupComponent("targetsTable")
@LoadDataBeforeShow
public class TargetBrowse extends StandardLookup<Target> {
}