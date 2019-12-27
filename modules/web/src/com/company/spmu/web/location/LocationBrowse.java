package com.company.spmu.web.location;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Location;

@UiController("spmu_Location.browse")
@UiDescriptor("location-browse.xml")
@LookupComponent("locationsTable")
@LoadDataBeforeShow
public class LocationBrowse extends StandardLookup<Location> {
}