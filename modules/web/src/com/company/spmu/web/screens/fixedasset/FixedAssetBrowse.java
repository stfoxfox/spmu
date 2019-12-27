package com.company.spmu.web.screens.fixedasset;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.FixedAsset;

@UiController("spmu_FixedAsset.browse")
@UiDescriptor("fixed-asset-browse.xml")
@LookupComponent("fixedAssetsTable")
@LoadDataBeforeShow
public class FixedAssetBrowse extends StandardLookup<FixedAsset> {
}