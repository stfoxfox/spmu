package com.company.spmu.web.applicationstageexpense.stages;

import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.company.spmu.web.applicationstageexpense.ApplicationStageExpenseEdit;

import javax.inject.Inject;

@UiController("spmu_ApplicationStageExpenseArticle20401.edit")
@UiDescriptor("application-stage-expense-article20401-edit.xml")
public class ApplicationStageExpenseArticle20401Edit extends ApplicationStageExpenseEdit {
    @Inject
    private CheckBox teachCh;
    @Inject
    private CheckBox expensesCh;
    @Inject
    private LookupField placeField;
    @Inject
    private TextField<String> qman1;
    @Inject
    private TextField<String> qman2;
    @Inject
    private TextField<String> qman3;
    @Inject
    private TextField<String> qman4;
    @Inject
    private LookupField nameTeachField;

    @Subscribe("teachCh")
    private void onTeachChValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.expensesCh.setValue(false);
            this.nameTeachField.setEditable(true);
            this.qman1.setEditable(true);
            this.qman2.setEditable(true);
            this.qman3.setEditable(true);
            this.qman4.setEditable(true);
        } else {
            this.nameTeachField.setEditable(false);
            this.qman1.setEditable(false);
            this.qman2.setEditable(false);
            this.qman3.setEditable(false);
            this.qman4.setEditable(false);
        }
    }

    @Subscribe("expensesCh")
    private void onExpensesChValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.teachCh.setValue(false);
            this.placeField.setEditable(true);
        } else {
            this.placeField.setEditable(false);
        }
    }
}
