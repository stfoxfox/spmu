package com.company.spmu.web.spmuuser;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.SpmuUser;

@UiController("spmu_SpmuUser.edit")
@UiDescriptor("spmu-user-edit.xml")
@EditedEntityContainer("spmuUserDc")
@LoadDataBeforeShow
public class SpmuUserEdit extends StandardEditor<SpmuUser> {
}