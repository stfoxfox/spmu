package com.company.spmu.web.screens.statuslink;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.StatusLink;

@UiController("spmu_StatusLink.browse")
@UiDescriptor("status-link-browse.xml")
@LookupComponent("statusLinksTable")
@LoadDataBeforeShow
public class StatusLinkBrowse extends StandardLookup<StatusLink> {
}