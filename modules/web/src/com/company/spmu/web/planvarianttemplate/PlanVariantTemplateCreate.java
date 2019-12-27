package com.company.spmu.web.planvarianttemplate;

import com.company.spmu.web.PlanVariantTemplateAction;
import com.haulmont.cuba.core.global.BeanValidation;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.PlanVariantTemplate;

import javax.inject.Inject;
import javax.validation.Validator;
import java.util.Objects;

@UiController("spmu_PlanVariantTemplate.create")
@UiDescriptor("plan-variant-template-create.xml")
@EditedEntityContainer("planVariantTemplateDc")
@LoadDataBeforeShow
public class PlanVariantTemplateCreate extends StandardEditor<PlanVariantTemplate> {
    @Inject
    private CheckBox newCh;
    @Inject
    private CheckBox templateCh;
    @Inject
    private LookupField templateField;
    @Inject
    private TextField<String> nameField;

    @Subscribe("newCh")
    private void onNewChValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.templateCh.setValue(false);
        }
    }

    @Subscribe("templateCh")
    private void onTemplateChValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.newCh.setValue(false);
            this.templateField.setEnabled(true);
        } else {
            this.templateField.setEnabled(false);
        }
    }

    @Subscribe("cancelBtn")
    private void onCancelBtnClick(Button.ClickEvent event) {
        this.closeWithDiscard();
    }

    @Subscribe("okBtn")
    private void onOkBtnClick(Button.ClickEvent event) {
        try {
            this.nameField.validate();
        }
        catch (ValidationException e) {
        }

        if (this.newCh.getValue()) {
            this.close(new PlanVariantTemplateAction(this.newCh.getValue(), this.nameField.getValue(), null));
        }
    }

    @Subscribe
    private void onInit(InitEvent event) {
        this.nameField.addValidator(s -> {
            if (s == null || Objects.equals(s, "")) {
                throw new ValidationException("Поле 'Название' не может быть пустым");
            }
        });
    }

}
