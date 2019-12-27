package com.company.spmu.web.planvariant;

import com.company.spmu.entity.*;
import com.company.spmu.enumeration.PlanVariantStatus;
import com.company.spmu.web.PlanVariantTemplateAction;
import com.company.spmu.web.bmp.fragment.procactionsfragment.ProcActionsFragment;
import com.company.spmu.web.planvarianttemplate.PlanVariantTemplateCreate;
import com.company.spmu.web.planvarianttemplate.PlanVariantTemplateEdit;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@UiController("spmu_PlanVariant.edit")
@UiDescriptor("plan-variant-edit.xml")
@EditedEntityContainer("planVariantDc")
@LoadDataBeforeShow
public class PlanVariantEdit extends StandardEditor<PlanVariant> {
    protected static final String PROCESS_CODE = "planvariantprocessmodel";
    private boolean isNew = false;
    private Employee employee = null;
    public Optional<PlanVariantTemplate> selectedTemplate = null;

    @Inject
    private TextField<Double> limit1;
    @Inject
    private TextField<Double> limit2;
    @Inject
    private TextField<Double> limit3;
    @Inject
    private TextField<Double> limit4;
    @Inject
    private TextField<Integer> yearField;
    @Inject
    private DataManager dataManager;
    @Inject
    private UserSession userSession;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private CollectionLoader<PlanVariantTemplate> planVariantTemplatesDl;
    @Inject
    private LookupField<PlanVariantTemplate> templateField;
    @Inject
    private ProcActionsFragment procActionsFragment;
    @Inject
    private CollectionContainer<PlanVariantTemplate> planVariantTemplatesDc;

    @Subscribe
    private void onInit(InitEvent event) {
        SpmuUser user = (SpmuUser) this.userSession.getUser();
        if (user != null) {
            this.employee = user.getEmployee();
        }

        this.yearField.addValidator(value -> {
            if (value == null) throw new ValidationException("Год не может быть пустым");
        });
    }

    @Subscribe
    private void onInitEntity(InitEntityEvent<PlanVariant> event) {
        PlanVariant entity = event.getEntity();
        entity.setDate(new Date());
        entity.setStatus(PlanVariantStatus.PROJECT);
        entity.setResponsible(this.employee);
        this.isNew = true;
    }

    @Subscribe
    private void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        PlanVariant edited = this.getEditedEntity();

        if (edited.getYear() == null) {
            event.preventCommit();
            throw new ValidationException("Неправильно задан год");
        }

        if (this.isNew) {
            List<PlanVariantLimit> list = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                PlanVariantLimit limit = event.getDataContext().create(PlanVariantLimit.class);
                limit.setYear(edited.getYear() + i);
                limit.setPlanVariant(edited);
                list.add(limit);
            }
            edited.setLimits(list);
        }

        try {
            edited.getLimits().get(0).setValue(this.limit1.getValue() != null ? this.limit1.getValue() : 0.0);
            edited.getLimits().get(1).setValue(this.limit2.getValue() != null ? this.limit2.getValue() : 0.0);
            edited.getLimits().get(2).setValue(this.limit3.getValue() != null ? this.limit3.getValue() : 0.0);
            edited.getLimits().get(3).setValue(this.limit4.getValue() != null ? this.limit4.getValue() : 0.0);
        }
        catch (Exception e) {}
    }

    @Subscribe
    private void initProcess(BeforeShowEvent event) {
        PlanVariant editedEntity = getEditedEntity();
        if (!PersistenceHelper.isNew(editedEntity)) {
            procActionsFragment.initializer().standard().init(PROCESS_CODE, editedEntity);
        }
    }

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        try {
            if (this.isNew) {
                this.limit1.setValue(0.0);
                this.limit2.setValue(0.0);
                this.limit3.setValue(0.0);
                this.limit4.setValue(0.0);
            } else {
                List<PlanVariantLimit> limits = this.getEditedEntity().getLimits();
                this.limit1.setValue(limits.get(0).getValue());
                this.limit2.setValue(limits.get(1).getValue());
                this.limit3.setValue(limits.get(2).getValue());
                this.limit4.setValue(limits.get(3).getValue());

                this.limit1.setCaption("Лимит финансирования на " + limits.get(0).getYear() + " год");
                this.limit2.setCaption("Лимит финансирования на " + limits.get(1).getYear() + " год");
                this.limit3.setCaption("Лимит финансирования на " + limits.get(2).getYear() + " год");
                this.limit4.setCaption("Лимит финансирования на " + limits.get(3).getYear() + " год");
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        this.planVariantTemplatesDl.setParameter("p", this.getEditedEntity());
    }

    @Subscribe("remove")
    private void onRemove(Action.ActionPerformedEvent event) {
        if (this.isNew) {
            this.closeWithDiscard();
        } else {
            this.dataManager.remove(this.getEditedEntity());
            close(new StandardCloseAction("remove"));
        }
    }

    @Subscribe("save")
    private void onSave(Action.ActionPerformedEvent event) {
        this.closeWithCommit();
    }

    @Subscribe("addview")
    private void onAddview(Action.ActionPerformedEvent event) {
        this.screenBuilders.editor(PlanVariantTemplate.class, this)
                .newEntity()
                .withLaunchMode(OpenMode.DIALOG)
                .withScreenClass(PlanVariantTemplateCreate.class)
                .withAfterCloseListener(planVariantTemplateCreateAfterScreenCloseEvent -> {
                    if (planVariantTemplateCreateAfterScreenCloseEvent.getCloseAction() instanceof PlanVariantTemplateAction) {
                        PlanVariantTemplateAction action = (PlanVariantTemplateAction) planVariantTemplateCreateAfterScreenCloseEvent.getCloseAction();
                        if (action.isNew) {
                            PlanVariantTemplate template = this.dataManager.create(PlanVariantTemplate.class);
                            template.setPlanVariant(this.getEditedEntity());
                            template.setName(action.name);
                            this.screenBuilders.editor(PlanVariantTemplate.class, this)
                                    .newEntity(template)
                                    .withLaunchMode(OpenMode.NEW_WINDOW)
                                    .withScreenClass(PlanVariantTemplateEdit.class)
                                    .build()
                                    .show();
                        }
                    }
                })
                .build()
                .show();
    }

    @Subscribe("planconstr")
    private void onPlanconstr(Action.ActionPerformedEvent event) {
        Optional<PlanVariantTemplate> selectedTemplate = this.planVariantTemplatesDc.getItems().stream().filter(PlanVariantTemplate::getActive).findAny();
        selectedTemplate.ifPresent(planVariantTemplate -> {
            PlanVariantTemplate selected = this.dataManager.load(PlanVariantTemplate.class).id(planVariantTemplate.getId()).view("planVariantTemplate-edit").one();
            this.screenBuilders.editor(PlanVariantTemplate.class, this)
                    .newEntity(selected)
                    .withLaunchMode(OpenMode.NEW_WINDOW)
                    .withScreenClass(PlanVariantTemplateEdit.class)
                    .build()
                    .show();
        });
    }

    @Subscribe
    private void onAfterShow(AfterShowEvent event) {
        this.selectedTemplate = this.planVariantTemplatesDc.getItems().stream().filter(PlanVariantTemplate::getActive).findAny();
        this.selectedTemplate.ifPresent(planVariantTemplate -> this.templateField.setValue(planVariantTemplate));
    }

    @Subscribe("templateField")
    private void onTemplateFieldValueChange(HasValue.ValueChangeEvent<PlanVariantTemplate> event) {
        PlanVariantTemplate selected = event.getValue();
        if (selected == null) {
            if (this.selectedTemplate.isPresent()) {
                this.selectedTemplate.get().setActive(false);
                this.dataManager.commit(this.selectedTemplate.get());
                this.selectedTemplate = Optional.of(selected);
            }
        } else {
            if (this.selectedTemplate.isPresent()) {
                if (!selected.equals(this.selectedTemplate.get())) {
                    selected.setActive(true);
                    this.dataManager.commit(selected);
                    this.selectedTemplate.get().setActive(false);
                    this.dataManager.commit(this.selectedTemplate.get());
                    this.selectedTemplate = Optional.of(selected);
                }
            } else {
                selected.setActive(true);
                this.dataManager.commit(selected);
                this.selectedTemplate = Optional.of(selected);
            }
        }
    }

}
