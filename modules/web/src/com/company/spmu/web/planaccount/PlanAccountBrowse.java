package com.company.spmu.web.planaccount;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.PlanAccount;

@UiController("spmu_PlanAccount.browse")
@UiDescriptor("plan-account-browse.xml")
@LookupComponent("planAccountsTable")
@LoadDataBeforeShow
public class PlanAccountBrowse extends StandardLookup<PlanAccount> {
}