package com.company.spmu.web.position;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Position;

@UiController("spmu_Position.edit")
@UiDescriptor("position-edit.xml")
@EditedEntityContainer("positionDc")
@LoadDataBeforeShow
public class PositionEdit extends StandardEditor<Position> {
}
