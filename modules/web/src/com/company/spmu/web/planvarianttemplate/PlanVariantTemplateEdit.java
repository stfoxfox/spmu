package com.company.spmu.web.planvarianttemplate;

import com.company.spmu.entity.PlanVariantTemplateElement;
import com.company.spmu.enumeration.PageElement;
import com.company.spmu.web.PlanVarianTemplateElementCloseAction;
import com.company.spmu.web.planvarianttemplateelement.PlanVariantTemplateElementEdit;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.PlanVariantTemplate;

import javax.inject.Inject;
import java.util.Optional;

@UiController("spmu_PlanVariantTemplate.edit")
@UiDescriptor("plan-variant-template-edit.xml")
@EditedEntityContainer("planVariantTemplateDc")
@LoadDataBeforeShow
public class PlanVariantTemplateEdit extends StandardEditor<PlanVariantTemplate> {
    private boolean isNew = false;

    @Inject
    private CollectionLoader<PlanVariantTemplate> planVariantTemplatesDl;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private DataContext dataContext;
    @Inject
    private TextField<String> pageFooterField;
    @Inject
    private TextField<String> pageHeaderField;
    @Inject
    private TextField<String> reportEndField;
    @Inject
    private TextField<String> titleField;

    @Subscribe("cancel")
    private void onCancel(Action.ActionPerformedEvent event) {
        this.closeWithDiscard();
    }

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        this.planVariantTemplatesDl.setParameter("p", this.getEditedEntity().getPlanVariant());

        if (!this.isNew) {
            // Устанавливаем значения полей на форме
            this.getEditedEntity().getElements().stream().forEach(planVariantTemplateElement -> {
                String s;
                if (planVariantTemplateElement.getAttributeName() != null) {
                    s = planVariantTemplateElement.getAttributeName();
                } else {
                    s = planVariantTemplateElement.getVariableValue();
                }
                if (s != null) {
                    switch (planVariantTemplateElement.getPageElement()) {
                        case PAGE_FOOTER:
                            this.pageFooterField.setValue(s);
                            break;
                        case PAGE_HEADER:
                            this.pageHeaderField.setValue(s);
                            break;
                        case REPORT_END:
                            this.reportEndField.setValue(s);
                            break;
                        case TITLE:
                            this.titleField.setValue(s);
                            break;
                    }
                }
            });
        }
    }

    @Subscribe("save")
    private void onSave(Action.ActionPerformedEvent event) {
        this.closeWithCommit();
    }

    @Subscribe("pageFooterAdd")
    private void onPageFooterAdd(Action.ActionPerformedEvent event) {
        this.openAddElementWindow(PageElement.PAGE_FOOTER, this.pageFooterField);
    }

    @Subscribe("pageHeaderAdd")
    private void onPageHeaderAdd(Action.ActionPerformedEvent event) {
        this.openAddElementWindow(PageElement.PAGE_HEADER, this.pageHeaderField);
    }

    @Subscribe("reportEndAdd")
    private void onReportEndAdd(Action.ActionPerformedEvent event) {
        this.openAddElementWindow(PageElement.REPORT_END, this.reportEndField);
    }

    @Subscribe("titleAdd")
    private void onTitleAdd(Action.ActionPerformedEvent event) {
        this.openAddElementWindow(PageElement.TITLE, this.titleField);
    }

    private void openAddElementWindow(PageElement element, TextField<String> field) {
        // Открываем модальное окно с выбором атрибута или строки
        Optional<PlanVariantTemplateElement> edit = this.getEditedEntity().getElements().stream().
                filter(planVariantTemplateElement -> planVariantTemplateElement.getPageElement().equals(element)).findAny();
        if (edit.isPresent()) {
            this.screenBuilders.editor(PlanVariantTemplateElement.class, this)
                    .withScreenClass(PlanVariantTemplateElementEdit.class)
                    .editEntity(edit.get())
                    .withLaunchMode(OpenMode.DIALOG)
                    .withAfterCloseListener(planVariantTemplateElementEditAfterScreenCloseEvent -> {
                        if (planVariantTemplateElementEditAfterScreenCloseEvent.getCloseAction() instanceof PlanVarianTemplateElementCloseAction) {
                            // Изменяем сущность PlanVariantTemplateElement
                            PlanVarianTemplateElementCloseAction action = (PlanVarianTemplateElementCloseAction) planVariantTemplateElementEditAfterScreenCloseEvent.getCloseAction();
                            edit.get().setPageElement(element);
                            if (action.attribute != "") {
                                edit.get().setAttributeName(action.attribute);
                                edit.get().setVariableValue(null);
                                field.setValue(action.attribute);
                            } else {
                                edit.get().setVariableValue(action.varValue);
                                edit.get().setAttributeName(null);
                                field.setValue(action.varValue);
                            }
                        }
                    })
                    .build()
                    .show();
        } else {
            this.screenBuilders.editor(PlanVariantTemplateElement.class, this)
                    .withScreenClass(PlanVariantTemplateElementEdit.class)
                    .newEntity()
                    .withLaunchMode(OpenMode.DIALOG)
                    .withAfterCloseListener(planVariantTemplateElementEditAfterScreenCloseEvent -> {
                        if (planVariantTemplateElementEditAfterScreenCloseEvent.getCloseAction() instanceof PlanVarianTemplateElementCloseAction) {
                            // Создаём сущность PlanVariantTemplateElement и добавляем её в контекст сохранения
                            PlanVarianTemplateElementCloseAction action = (PlanVarianTemplateElementCloseAction) planVariantTemplateElementEditAfterScreenCloseEvent.getCloseAction();
                            PlanVariantTemplateElement el = this.dataContext.create(PlanVariantTemplateElement.class);
                            el.setPlanVariantTemplate(this.getEditedEntity());
                            el.setPageElement(element);
                            if (action.attribute != "") {
                                el.setAttributeName(action.attribute);
                                field.setValue(action.attribute);
                            } else {
                                el.setVariableValue(action.varValue);
                                field.setValue(action.varValue);
                            }
                            this.getEditedEntity().getElements().add(el);
                        }
                    })
                    .build()
                    .show();
        }
    }

    @Subscribe
    private void onInitEntity(InitEntityEvent<PlanVariantTemplate> event) {
        this.isNew = true;
    }

}
