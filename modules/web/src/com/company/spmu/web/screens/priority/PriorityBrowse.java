package com.company.spmu.web.screens.priority;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Priority;

@UiController("spmu_Priority.browse")
@UiDescriptor("priority-browse.xml")
@LookupComponent("prioritiesTable")
@LoadDataBeforeShow
public class PriorityBrowse extends StandardLookup<Priority> {
}