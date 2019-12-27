package com.company.spmu.web.mandate;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Mandate;

@UiController("spmu_Mandate.browse")
@UiDescriptor("mandate-browse.xml")
@LookupComponent("mandatesTable")
@LoadDataBeforeShow
public class MandateBrowse extends StandardLookup<Mandate> {
}