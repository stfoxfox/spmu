package com.company.spmu.web.contractstage;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ContractStage;

@UiController("spmu_ContractStage.browse")
@UiDescriptor("contract-stage-browse.xml")
@LookupComponent("contractStagesTable")
@LoadDataBeforeShow
public class ContractStageBrowse extends StandardLookup<ContractStage> {
}