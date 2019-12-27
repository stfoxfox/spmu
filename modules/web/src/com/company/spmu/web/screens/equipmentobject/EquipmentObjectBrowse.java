package com.company.spmu.web.screens.equipmentobject;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.EquipmentObject;

@UiController("spmu_EquipmentObject.browse")
@UiDescriptor("equipment-object-browse.xml")
@LookupComponent("equipmentObjectsTable")
@LoadDataBeforeShow
public class EquipmentObjectBrowse extends StandardLookup<EquipmentObject> {
}