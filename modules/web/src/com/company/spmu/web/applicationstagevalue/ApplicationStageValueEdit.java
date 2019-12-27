package com.company.spmu.web.applicationstagevalue;

import com.company.spmu.entity.ApplicationStage;
import com.company.spmu.repositories.entity.ApplicationStageRepository;
import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.gui.components.HBoxLayout;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ApplicationStageValue;

import javax.inject.Inject;
import java.util.Calendar;

@UiController("spmu_ApplicationStageValue.edit")
@UiDescriptor("application-stage-value-edit.xml")
@EditedEntityContainer("applicationStageValueDc")
@LoadDataBeforeShow
public class ApplicationStageValueEdit extends StandardEditor<ApplicationStageValue> {
    private boolean qmode = false;
    private ApplicationStageValue topParent = null;

    @Inject
    private HBoxLayout modeM1;
    @Inject
    private HBoxLayout modeM2;
    @Inject
    private HBoxLayout modeM3;
    @Inject
    private HBoxLayout modeQ;
    @Inject
    private TextField<Double> m1Field;
    @Inject
    private TextField<Double> m2Field;
    @Inject
    private TextField<Double> m3Field;
    @Inject
    private TextField<Double> m4Field;
    @Inject
    private TextField<Double> m5Field;
    @Inject
    private TextField<Double> m6Field;
    @Inject
    private TextField<Double> m7Field;
    @Inject
    private TextField<Double> m8Field;
    @Inject
    private TextField<Double> m9Field;
    @Inject
    private TextField<Double> m10Field;
    @Inject
    private TextField<Double> m11Field;
    @Inject
    private TextField<Double> m12Field;
    @Inject
    private TextField<Double> q1Field;
    @Inject
    private TextField<Double> q2Field;
    @Inject
    private TextField<Double> q3Field;
    @Inject
    private TextField<Double> q4Field;
    @Inject
    private TextField<String> idField;
    @Inject
    private TextField<Double> sumMField;
    @Inject
    private TextField<Double> sumQField;
    @Inject
    private ApplicationStageRepository applicationStageRepository;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        this.topParent = this.getEditedEntity();
        if (this.topParent.getApplicationStage() == null) {
            this.topParent = this.topParent.getParent();
        }
        if (this.topParent != null && this.topParent.getApplicationStage() != null) {
            this.qmode = this.topParent.getApplicationStage().getIs_quartal();
            if (this.qmode) {
                this.modeM1.setVisible(false);
                this.modeM2.setVisible(false);
                this.modeM3.setVisible(false);
                this.modeQ.setVisible(true);
            } else {
                this.modeQ.setVisible(false);
                this.modeM1.setVisible(true);
                this.modeM2.setVisible(true);
                this.modeM3.setVisible(true);
            }
            int num = this.getEditedEntity().getYear() - this.getParentStartYear() + 1;
            this.idField.setValue(String.valueOf(num));
        }
    }

    @Subscribe
    private void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        this.getEditedEntity().setValue(this.getSummary()); // общая сумма
        if (!this.qmode) {
            // сумма поквартально
            try {
                this.q1Field.setValue(this.m1Field.getValue() + this.m2Field.getValue() + this.m3Field.getValue());
            }
            catch (NullPointerException e) {
                this.q1Field.setValue(0.0);
            }
            try {
                this.q2Field.setValue(this.m4Field.getValue() + this.m5Field.getValue() + this.m6Field.getValue());
            }
            catch (NullPointerException e) {
                this.q2Field.setValue(0.0);
            }
            try {
                this.q3Field.setValue(this.m7Field.getValue() + this.m8Field.getValue() + this.m9Field.getValue());
            }
            catch (NullPointerException e) {
                this.q3Field.setValue(0.0);
            }
            try {
                this.q4Field.setValue(this.m10Field.getValue() + this.m11Field.getValue() + this.m12Field.getValue());
            }
            catch (NullPointerException e) {
                this.q4Field.setValue(0.0);
            }
        }
    }

    private Double getSummary() {
        try {
            if (this.qmode) {
                return this.q1Field.getValue() + this.q2Field.getValue() + this.q3Field.getValue() + this.q4Field.getValue();
            } else {
                return this.m1Field.getValue() + this.m2Field.getValue() + this.m3Field.getValue() + this.m4Field.getValue() + this.m5Field.getValue() +
                        this.m6Field.getValue() + this.m7Field.getValue() + this.m8Field.getValue() + this.m9Field.getValue() + this.m10Field.getValue() +
                        this.m11Field.getValue() + this.m12Field.getValue();
            }
        }
        catch (NullPointerException e) {
            return 0.0;
        }
    }

    private int getParentStartYear() {
        final Calendar c = Calendar.getInstance();
        c.setTime(applicationStageRepository.findOne(this.topParent.getApplicationStage().getId()).getStart());
        return c.get(Calendar.YEAR);
    }

    @Subscribe("m1Field")
    private void onM1FieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        this.updatedMFields();
    }

    @Subscribe("m2Field")
    private void onM2FieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        this.updatedMFields();
    }

    @Subscribe("m3Field")
    private void onM3FieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        this.updatedMFields();
    }

    @Subscribe("m4Field")
    private void onM4FieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        this.updatedMFields();
    }

    @Subscribe("m5Field")
    private void onM5FieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        this.updatedMFields();
    }

    @Subscribe("m6Field")
    private void onM6FieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        this.updatedMFields();
    }

    @Subscribe("m7Field")
    private void onM7FieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        this.updatedMFields();
    }

    @Subscribe("m8Field")
    private void onM8FieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        this.updatedMFields();
    }

    @Subscribe("m9Field")
    private void onM9FieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        this.updatedMFields();
    }

    @Subscribe("m10Field")
    private void onM10FieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        this.updatedMFields();
    }

    @Subscribe("m11Field")
    private void onM11FieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        this.updatedMFields();
    }

    @Subscribe("m12Field")
    private void onM12FieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        this.updatedMFields();
    }

    @Subscribe("q1Field")
    private void onQ1FieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        this.updatedQFields();
    }

    @Subscribe("q2Field")
    private void onQ2FieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        this.updatedQFields();
    }

    @Subscribe("q3Field")
    private void onQ3FieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        this.updatedQFields();
    }

    @Subscribe("q4Field")
    private void onQ4FieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        this.updatedQFields();
    }

    private void updatedQFields() {
        this.sumQField.setValue(this.getSummary());
    }

    private void updatedMFields() {
        this.sumMField.setValue(this.getSummary());
    }

}
