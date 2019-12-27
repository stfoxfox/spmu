package com.company.spmu.web.finsource;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Finsource;

@UiController("spmu_Finsource.browse")
@UiDescriptor("finsource-browse.xml")
@LookupComponent("finsourcesTable")
@LoadDataBeforeShow
public class FinsourceBrowse extends StandardLookup<Finsource> {
}