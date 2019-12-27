package com.company.spmu.web.eventtype;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.EventType;

@UiController("spmu_EventType.browse")
@UiDescriptor("event-type-browse.xml")
@LookupComponent("eventTypesTable")
@LoadDataBeforeShow
public class EventTypeBrowse extends StandardLookup<EventType> {
}