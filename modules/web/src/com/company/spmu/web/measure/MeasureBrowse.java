package com.company.spmu.web.measure;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Measure;

@UiController("spmu_Measure.browse")
@UiDescriptor("measure-browse.xml")
@LookupComponent("measuresTable")
@LoadDataBeforeShow
public class MeasureBrowse extends StandardLookup<Measure> {
}