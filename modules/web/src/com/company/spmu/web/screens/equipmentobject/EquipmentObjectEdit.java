package com.company.spmu.web.screens.equipmentobject;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.EquipmentObject;

@UiController("spmu_EquipmentObject.edit")
@UiDescriptor("equipment-object-edit.xml")
@EditedEntityContainer("equipmentObjectDc")
@LoadDataBeforeShow
public class EquipmentObjectEdit extends StandardEditor<EquipmentObject> {
}