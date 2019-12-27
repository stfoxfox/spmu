package com.company.spmu.web.workflow;

import com.company.spmu.web.custom.editors.UuidStandardEditor;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Workflow;

@UiController("spmu_Workflow.edit")
@UiDescriptor("workflow-edit.xml")
@EditedEntityContainer("workflowDc")
@LoadDataBeforeShow
public class WorkflowEdit extends UuidStandardEditor<Workflow> {
}