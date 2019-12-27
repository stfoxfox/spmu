package com.company.spmu.web.account;

import com.company.spmu.web.custom.editors.UuidStandardEditor;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Account;

@UiController("spmu_Account.edit")
@UiDescriptor("account-edit.xml")
@EditedEntityContainer("accountDc")
@LoadDataBeforeShow
public class AccountEdit extends UuidStandardEditor<Account> {
}