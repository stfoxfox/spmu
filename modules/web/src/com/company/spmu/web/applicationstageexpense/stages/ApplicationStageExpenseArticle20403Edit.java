package com.company.spmu.web.applicationstageexpense.stages;

import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.company.spmu.web.applicationstageexpense.ApplicationStageExpenseEdit;

import javax.inject.Inject;

@UiController("spmu_ApplicationStageExpenseArticle20403.edit")
@UiDescriptor("application-stage-expense-article20403-edit.xml")
public class ApplicationStageExpenseArticle20403Edit extends ApplicationStageExpenseEdit {
    @Inject
    private CheckBox expensesCh;
    @Inject
    private CheckBox registerCh;

    @Subscribe("expensesCh")
    private void onExpensesChValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.registerCh.setValue(false);
        } else {
        }
    }

    @Subscribe("registerCh")
    private void onRegisterChValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.expensesCh.setValue(false);
        } else {
        }
    }

}
