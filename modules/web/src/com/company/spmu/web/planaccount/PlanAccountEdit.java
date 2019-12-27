package com.company.spmu.web.planaccount;

import com.company.spmu.web.custom.editors.UuidStandardEditor;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.PlanAccount;

@UiController("spmu_PlanAccount.edit")
@UiDescriptor("plan-account-edit.xml")
@EditedEntityContainer("planAccountDc")
@LoadDataBeforeShow
public class PlanAccountEdit extends UuidStandardEditor<PlanAccount> {
}