package com.company.spmu.web.screens.statuslink;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.StatusLink;

@UiController("spmu_StatusLink.edit")
@UiDescriptor("status-link-edit.xml")
@EditedEntityContainer("statusLinkDc")
@LoadDataBeforeShow
public class StatusLinkEdit extends StandardEditor<StatusLink> {
}