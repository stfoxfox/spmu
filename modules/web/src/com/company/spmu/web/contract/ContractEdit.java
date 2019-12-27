package com.company.spmu.web.contract;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Contract;
import javax.inject.Inject;
import java.util.Date;
import com.haulmont.cuba.gui.components.*;

@UiController("spmu_Contract.edit")
@UiDescriptor("contract-edit.xml")
@EditedEntityContainer("contractDc")
@LoadDataBeforeShow
public class ContractEdit extends StandardEditor<Contract> {
    @Inject
    private DateField<Date> startField;
    @Inject
    private DateField<Date> endField;


    @Subscribe
    private void addValidators(AfterShowEvent event) {
        this.startField.addValidator(value -> {
            if(value.after(this.endField.getValue())){
                throw new ValidationException("Дата начала должна быть меньше или равна дате окончания");
            }
        });
    }

}