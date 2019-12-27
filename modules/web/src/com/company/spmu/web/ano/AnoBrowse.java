package com.company.spmu.web.ano;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Ano;

@UiController("spmu_Ano.browse")
@UiDescriptor("ano-browse.xml")
@LookupComponent("anoesTable")
@LoadDataBeforeShow
public class AnoBrowse extends StandardLookup<Ano> {
}