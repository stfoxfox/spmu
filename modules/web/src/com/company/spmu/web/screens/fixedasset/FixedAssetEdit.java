package com.company.spmu.web.screens.fixedasset;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.FixedAsset;

@UiController("spmu_FixedAsset.edit")
@UiDescriptor("fixed-asset-edit.xml")
@EditedEntityContainer("fixedAssetDc")
@LoadDataBeforeShow
public class FixedAssetEdit extends StandardEditor<FixedAsset> {
}