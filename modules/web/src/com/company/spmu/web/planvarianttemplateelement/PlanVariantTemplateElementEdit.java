package com.company.spmu.web.planvarianttemplateelement;

import com.company.spmu.web.PlanVarianTemplateElementCloseAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.PlanVariantTemplateElement;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("spmu_PlanVariantTemplateElement.edit")
@UiDescriptor("plan-variant-template-element-edit.xml")
@EditedEntityContainer("planVariantTemplateElementDc")
@LoadDataBeforeShow
public class PlanVariantTemplateElementEdit extends StandardEditor<PlanVariantTemplateElement> {
    private boolean isNew = false;

    @Inject
    private LookupField attributeField;
    @Inject
    private CheckBox attributeCh;
    @Inject
    private CheckBox variableCh;
    @Inject
    private TextField<String> varValueField;

    @Subscribe
    private void onInitEntity(InitEntityEvent<PlanVariantTemplateElement> event) {
        this.isNew = true;
    }

    @Subscribe
    private void onInit(InitEvent event) {
        List<String> list = new ArrayList<>();
        list.add("code");
        list.add("year");
        list.add("date");
        list.add("status");
        list.add("name");
        list.add("description");
        list.add("limit0");
        list.add("limit1");
        list.add("limit2");
        list.add("limit3");
        this.attributeField.setOptionsList(list);
    }

    @Subscribe("attributeCh")
    private void onAttributeChValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.variableCh.setValue(false);
            this.attributeField.setEnabled(true);
        } else {
            this.attributeField.setEnabled(false);
        }
    }

    @Subscribe("variableCh")
    private void onVariableChValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.attributeCh.setValue(false);
            this.varValueField.setEnabled(true);
        } else {
            this.varValueField.setEnabled(false);
        }
    }

    @Subscribe("save")
    private void onSave(Action.ActionPerformedEvent event) {
        String attribute = "";
        String varValue = "";
        if (this.attributeCh.getValue()) {
            attribute = (String) this.attributeField.getValue();
        } else if (this.variableCh.getValue()) {
            varValue = this.varValueField.getValue();
        }
        this.close(new PlanVarianTemplateElementCloseAction(attribute, varValue));
    }

    @Subscribe("cancel")
    private void onCancel(Action.ActionPerformedEvent event) {
        this.closeWithDiscard();
    }

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        if (!this.isNew) {
            if (this.getEditedEntity().getAttributeName() != null) {
                this.attributeCh.setValue(true);
                this.attributeField.setValue(this.getEditedEntity().getAttributeName());
            } else {
                this.variableCh.setValue(true);
                this.varValueField.setValue(this.getEditedEntity().getVariableValue());
            }
        }
    }

}
