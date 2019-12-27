package com.company.spmu.web.screens.connectionlog;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ConnectionLog;

@UiController("spmu_ConnectionLog.browse")
@UiDescriptor("connection-log-browse.xml")
@LookupComponent("connectionLogsTable")
@LoadDataBeforeShow
public class ConnectionLogBrowse extends StandardLookup<ConnectionLog> {
}