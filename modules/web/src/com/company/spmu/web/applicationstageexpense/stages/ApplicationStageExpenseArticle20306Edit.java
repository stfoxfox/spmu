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

@UiController("spmu_ApplicationStageExpenseArticle20306.edit")
@UiDescriptor("application-stage-expense-article20306-edit.xml")
public class ApplicationStageExpenseArticle20306Edit extends ApplicationStageExpenseEdit {

    @Inject
    private TextField<String> fuel100Id;
    @Inject
    private TextField<String> fuelPriceId;
    @Inject
    private TextField<String> fuelYearId;
    @Inject
    private TextField<String> hourId;
    @Inject
    private TextField<String> outfitId;
    @Inject
    private TextField<String> priceId;
    @Inject
    private TextField<String> probegId;
    @Inject
    private LookupField transportId;
    @Inject
    private LookupField typeId;
    @Inject
    private LookupField viewId;
    @Inject
    private TextField<String> yearConsumptionId;
    @Inject
    private CheckBox autoCh;
    @Inject
    private CheckBox techCh;

    @Subscribe("techCh")
    private void onTechChValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.autoCh.setValue(false);
            this.priceId.setEditable(true);
            this.hourId.setEditable(true);
            this.outfitId.setEditable(true);
            this.yearConsumptionId.setEditable(true);
        } else {
            this.priceId.setEditable(false);
            this.hourId.setEditable(false);
            this.outfitId.setEditable(false);
            this.yearConsumptionId.setEditable(false);
        }
    }

    @Subscribe("autoCh")
    private void onAutoChValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.techCh.setValue(false);
            this.typeId.setEditable(true);
            this.viewId.setEditable(true);
            this.transportId.setEditable(true);
            this.probegId.setEditable(true);
            this.fuel100Id.setEditable(true);
            this.fuelYearId.setEditable(true);
            this.fuelPriceId.setEditable(true);
        } else {
            this.typeId.setEditable(false);
            this.viewId.setEditable(false);
            this.transportId.setEditable(false);
            this.probegId.setEditable(false);
            this.fuel100Id.setEditable(false);
            this.fuelYearId.setEditable(false);
            this.fuelPriceId.setEditable(false);
        }
    }

}
