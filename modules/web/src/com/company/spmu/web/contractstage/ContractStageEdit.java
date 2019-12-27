package com.company.spmu.web.contractstage;

import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ContractStage;

import javax.inject.Inject;
import java.util.Date;

@UiController("spmu_ContractStage.edit")
@UiDescriptor("contract-stage-edit.xml")
@EditedEntityContainer("contractStageDc")
@LoadDataBeforeShow
public class ContractStageEdit extends StandardEditor<ContractStage> {
    @Inject
    private DateField<Date> startField;
    @Inject
    private DateField<Date> endField;


    @Subscribe
    private void addValidators(AfterShowEvent event) {
        this.startField.addValidator(value -> {
            if(value.after(this.endField.getValue())){
                throw new ValidationException("Дата начала должна быть меньше или равна дате окончания ");
            }
        });
    }

}