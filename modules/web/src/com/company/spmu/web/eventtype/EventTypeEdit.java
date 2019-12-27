package com.company.spmu.web.eventtype;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.EventType;

@UiController("spmu_EventType.edit")
@UiDescriptor("event-type-edit.xml")
@EditedEntityContainer("eventTypeDc")
@LoadDataBeforeShow
public class EventTypeEdit extends StandardEditor<EventType> {
}