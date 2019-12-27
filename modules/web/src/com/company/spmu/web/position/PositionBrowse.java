package com.company.spmu.web.position;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Position;

@UiController("spmu_Position.browse")
@UiDescriptor("position-browse.xml")
@LookupComponent("positionsTable")
@LoadDataBeforeShow
public class PositionBrowse extends StandardLookup<Position> {
}
