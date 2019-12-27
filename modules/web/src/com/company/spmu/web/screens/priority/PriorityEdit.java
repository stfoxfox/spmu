package com.company.spmu.web.screens.priority;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Priority;

@UiController("spmu_Priority.edit")
@UiDescriptor("priority-edit.xml")
@EditedEntityContainer("priorityDc")
@LoadDataBeforeShow
public class PriorityEdit extends StandardEditor<Priority> {
}