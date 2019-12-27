package com.company.spmu.web.spmuuser;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.SpmuUser;

@UiController("spmu_SpmuUser.browse")
@UiDescriptor("spmu-user-browse.xml")
@LookupComponent("usersTable")
@LoadDataBeforeShow
public class SpmuUserBrowse extends StandardLookup<SpmuUser> {
}