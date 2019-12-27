package com.company.spmu.web.applicationstageexpense.stages;

import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.company.spmu.web.applicationstageexpense.ApplicationStageExpenseEdit;

import javax.inject.Inject;

@UiController("spmu_ApplicationStageExpenseArticle20404.edit")
@UiDescriptor("application-stage-expense-article20404-edit.xml")
public class ApplicationStageExpenseArticle20404Edit extends ApplicationStageExpenseEdit {
    @Inject
    private TextField<String> rentField;
    @Inject
    private TextField<String> workField;
    @Inject
    private CheckBox rentCh;
    @Inject
    private CheckBox registerCh;

    @Subscribe("registerCh")
    private void onRegisterChValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.rentCh.setValue(false);
            this.workField.setEditable(true);
        } else {
            this.workField.setEditable(false);
        }
    }

    @Subscribe("rentCh")
    private void onRentChValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.registerCh.setValue(false);
            this.rentField.setEditable(true);
        } else {
            this.rentField.setEditable(false);
        }
    }
}
