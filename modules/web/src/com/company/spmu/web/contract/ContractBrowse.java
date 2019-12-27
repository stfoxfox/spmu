package com.company.spmu.web.contract;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Contract;

@UiController("spmu_Contract.browse")
@UiDescriptor("contract-browse.xml")
@LookupComponent("contractsTable")
@LoadDataBeforeShow
public class ContractBrowse extends StandardLookup<Contract> {
}