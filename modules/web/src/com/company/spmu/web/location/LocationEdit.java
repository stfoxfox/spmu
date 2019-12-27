package com.company.spmu.web.location;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Location;

@UiController("spmu_Location.edit")
@UiDescriptor("location-edit.xml")
@EditedEntityContainer("locationDc")
@LoadDataBeforeShow
public class LocationEdit extends StandardEditor<Location> {
}