package com.company.spmu.web.account;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Account;

@UiController("spmu_Account.browse")
@UiDescriptor("account-browse.xml")
@LookupComponent("accountsTable")
@LoadDataBeforeShow
public class AccountBrowse extends StandardLookup<Account> {
}