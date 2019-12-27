package com.company.spmu.web.ano;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Ano;

@UiController("spmu_Ano.edit")
@UiDescriptor("ano-edit.xml")
@EditedEntityContainer("anoDc")
@LoadDataBeforeShow
public class AnoEdit extends StandardEditor<Ano> {
}