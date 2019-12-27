package com.company.spmu.web.workflow;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Workflow;

@UiController("spmu_Workflow.browse")
@UiDescriptor("workflow-browse.xml")
@LookupComponent("workflowsTable")
@LoadDataBeforeShow
public class WorkflowBrowse extends StandardLookup<Workflow> {
}