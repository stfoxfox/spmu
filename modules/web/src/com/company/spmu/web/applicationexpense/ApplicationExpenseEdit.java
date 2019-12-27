package com.company.spmu.web.applicationexpense;

import com.company.spmu.entity.*;
import com.company.spmu.enumeration.ApplicationResponsibleRole;
import com.company.spmu.service.AppExpenseService;
import com.company.spmu.service.AppIncomeService;
import com.company.spmu.web.applicationhistory.ApplicationHistoryBrowse;
import com.company.spmu.web.applicationstageexpense.ApplicationStageExpenseBrowseFragment;
import com.company.spmu.web.bmp.fragment.procactionsfragment.ProcActionsFragment;
import com.company.spmu.web.correctionhistory.ApplicationCorrectionHistoryFragment;
import com.haulmont.bpm.entity.ProcAttachment;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@UiController("spmu_ApplicationExpense.edit")
@UiDescriptor("application-expense-edit.xml")
@EditedEntityContainer("applicationExpenseDc")
@LoadDataBeforeShow
public class ApplicationExpenseEdit extends StandardEditor<ApplicationExpense> {
    protected static final String PROCESS_CODE = "applicationexpenseprocessmodel";
    private boolean isNew = false;
    private boolean forceSave = false;
    private Employee employee = null;
    private ApplicationExpense original;

    @Inject
    protected Button save;
//    @Inject
//    private Button print;

    @Inject
    public InstanceContainer applicationExpenseDc;
    @Inject
    protected Label titleLabel;
    @Inject
    private DataManager dataManager;
    @Inject
    private Button changesBtn;
    @Inject
    private Button accHistoryBtn;
//    @Inject
//    private Button createCorBtn;
//    @Inject
//    private Button toArchive;
//    @Inject
//    private Button cancelAcceptBtn;
    @Inject
    private TextField<String> valueField;
    @Inject
    private LookupPickerField<Affilate> affiliateField;
    @Inject
    private CollectionLoader<Division> divisionsDl;
    @Inject
    private LookupField<Division> divisionField;
    @Inject
    private LookupField<Employee> initiator;
    @Inject
    private AppExpenseService appExpenseService;
    @Inject
    private Fragment stagebrowser;
    @Inject
    private TabSheet tabs;
    @Inject
    private CollectionLoader<ProcAttachment> procAttachmentsDl;
    @Inject
    private ProcActionsFragment procActionsFragment;
    @Inject
    private UserSession userSession;
    @Inject
    private TokenList<ApplicationEditor> editors;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Metadata metadata;
    @Inject
    private EntityStates entityStates;
    @Inject
    private UserSessionSource userSessionSource;
    @Inject
    private AppIncomeService appIncomeService;
    @Inject
    private Dialogs dialogs;
    @Inject
    private TextField<String> planTemplateField;

    /**
     * проверка на дубли редакторов
     * */
    @Subscribe("editors")
    private void onEditorsValueChange(HasValue.ValueChangeEvent<Collection<ApplicationEditor>> collectionValueChangeEvent) {
        List<ApplicationEditor> applicationDefautlEditors = this.getEditedEntity().getApplicationResponsibleEditors();//((ApplicationExpense) applicationExpenseDc.getItem()).getApplicationResponsibleEditors();
        List<ApplicationEditor> applicationEditors = new ArrayList<>();
        for (int i = 0; i < applicationDefautlEditors.size(); i ++) {
            if (applicationDefautlEditors.get(i) instanceof ApplicationEditor) {
                applicationEditors.add(applicationDefautlEditors.get(i));
            }
        }

        for (int i = 0; i < applicationDefautlEditors.size(); i++) {
            if (!applicationEditors.contains(applicationDefautlEditors.get(i))) {
                for (ApplicationEditor editor: applicationEditors) {
                    if(editor.getEmployee().equals(applicationDefautlEditors.get(i))) {
                        applicationDefautlEditors.remove(i);
                        break;
                    }
                }
            }
        }
        ((ApplicationExpense) applicationExpenseDc.getItem()).setApplicationResponsibleEditors(applicationDefautlEditors);
        editors.setValue(applicationDefautlEditors);
    }


    /**
     * Измененилось Подразделение - загружаем соотв. новый список Отделов
     * @param event
     */
    @Subscribe("affiliateField")
    private void onAffiliateFieldValueChange(HasValue.ValueChangeEvent<Affilate> event) {
        this.checkAffiliateField();
    }

    /**
     * Вызывается только для новой сущности
     * @param event
     */
    @Subscribe
    public void onInitEntity(InitEntityEvent<ApplicationExpense> event) {
        // Дефортные значения для новой записи
        event.getEntity().setResponsible(this.employee);
        ApplicationExpense app = event.getEntity();

        // Скрываем кнопки для новой записи
//        this.print.setEnabled(false);
        this.changesBtn.setEnabled(false);
        this.accHistoryBtn.setEnabled(false);
//        this.createCorBtn.setEnabled(false);
//        this.toArchive.setEnabled(false);
//        this.cancelAcceptBtn.setEnabled(false);

        // Установка значения поле Инициатор
        SpmuUser user = (SpmuUser) this.userSession.getUser();
        ApplicationInitiator ai = this.getEditedEntity().getApplicationResponsibleInitiator();
        if (ai != null) {
            this.initiator.setValue(ai.getEmployee());
        } else {
            this.initiator.setValue(user.getEmployee());
        }
        // Добавляем текущего пользователя в редакторы

        // Добавляем текущего пользователя в редакторы
        ApplicationEditor editor = this.createEditor(this.employee, app);
        List<ApplicationEditor> listEditors = new ArrayList<>();
        listEditors.add(editor);
        app.setApplicationResponsibleEditors(listEditors);
    }

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        this.isNew = PersistenceHelper.isNew(this.getEditedEntity());
        this.original = this.getEditedEntity();
        this.checkAffiliateField();
        if (!this.isNew) {
            // Заголовки
            String title = "Заявка на расходы ";
            title += ((this.original.getCode() != null ? " №" + this.original.getCode() : "") + (this.original.getName() != null ? " \"" + this.original.getName()+"\"" : ""));
            titleLabel.setValue(title);
            this.getWindow().setCaption("Заявка на расходы" + (this.original.getCode() != null ? " №" + this.original.getCode() : "")  );
            this.tabs.getTab("editortab").setCaption("Заявка на расходы" + (this.original.getCode() != null ? " №" + this.original.getCode() : ""));
            this.tabs.getTab("articles_list").setCaption("Статьи заявки" +(this.original.getCode() != null ? " №" + this.original.getCode() : ""));
        }
        else {
            String title = "Создание заявки на расходы ";
            titleLabel.setValue(title);
        }

        this.valueField.setValue("В тыс.руб.");

        // Поле "План"
        if (this.original != null && this.original.getPlanVariantTemplateApplications() != null && !this.original.getPlanVariantTemplateApplications().isEmpty()
                && this.original.getPlanVariantTemplateApplications().get(0).getPlanVariantTemplate() != null)
        {
            this.planTemplateField.setValue(this.original.getPlanVariantTemplateApplications().get(0).getPlanVariantTemplate().getPlanVariant().getName());
        }
    }

    @Subscribe
    private void onInit(InitEvent event) {
        SpmuUser user = (SpmuUser) this.userSession.getUser();
        if (user != null) {
            this.employee = user.getEmployee();
        }
    }

    @Subscribe
    private void onAfterShow(AfterShowEvent event) {
        // Установка значения поле Инициатор
        ApplicationResponsible ai = this.getEditedEntity().getApplicationResponsibleInitiator();
        if (ai != null) {
            this.initiator.setValue(ai.getEmployee());
        }

        ApplicationStageExpenseBrowseFragment stageBrowser = (ApplicationStageExpenseBrowseFragment) this.stagebrowser.getFrameOwner();
        stageBrowser.setParentApp(this.getEditedEntity());
    }

    /**
     * После изменения Подразделения загружаем соотв. новый список Отделов
     */
    private void checkAffiliateField() {
        this.divisionField.setEditable(false);
        this.divisionField.setValue(null);
        this.divisionsDl.setQuery("select e from spmu_Division e where e.affiliate=:affiliate");
        this.divisionsDl.setParameter("affiliate", this.affiliateField.getValue());
        this.divisionsDl.load();
        if(!this.divisionsDl.getContainer().getItems().isEmpty() && this.affiliateField.getValue() != null){
            this.divisionField.setEditable(true);
        }
    }

    /**
     * После сохранения основных данных сущности, сохраняем те что не сохранились автоматически
     * @param event
     */
    @Subscribe(target = Target.DATA_CONTEXT)
    private void onPostCommit(DataContext.PostCommitEvent event) {
        ApplicationExpense applicationExpense = this.getEditedEntity();

        // Сохранение инициатора
        Employee employee = this.initiator.getValue();
        if (employee != null) {
            this.appIncomeService.saveInitiator(applicationExpense, employee);
        }

        if (this.isNew) {
            this.createPlanArticleStages(applicationExpense);
        }

    }

    @Subscribe(target = Target.DATA_CONTEXT)
    private void onPreCommit(DataContext.PreCommitEvent event) {
        int year = this.getEditedEntity().getYear();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateStart = format.parse( (Integer.toString(year) +"-01-01" ));
            Date dateEnd = format.parse( (Integer.toString(year) +"-12-31" ));
            this.getEditedEntity().setDateStart(dateStart);
            this.getEditedEntity().setDateEnd(dateEnd);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }

        // Сохранение списка редакторов
        boolean found = false;
        ApplicationExpense appEvent = this.getEditedEntity();
        List<?> editors = applicationExpenseDc.getItem().getValue("applicationResponsibleEditors");
        if (editors != null) {
            int i = 0;

            while (i < editors.size()) {
                if (editors.get(i) instanceof Employee) {
                    ApplicationEditor editor = this.createEditor((Employee) editors.get(i), appEvent);
                    event.getModifiedInstances().add(editor);
                    if (editors.get(i).equals(this.employee)) {
                        found = true;
                    }
                } else if (editors.get(i) instanceof ApplicationEditor) {
                    if (((ApplicationEditor) editors.get(i)).getEmployee().equals(this.employee)) {
                        found = true;
                        if (this.entityStates.isNew(editors.get(i))) {
                            ApplicationEditor editor = (ApplicationEditor) editors.get(i);
                            event.getModifiedInstances().add(editor);
                        }
                    }
                }
                i++;
            }
            // Текущего пользователя добавляем в редакторы если его там нет
            if (!found && this.employee != null) {
                ApplicationEditor editor = this.createEditor(this.employee, appEvent);
                event.getModifiedInstances().add(editor);
            }
        }
    }

    @Subscribe
    private void initProcess(AfterShowEvent event) {
        ApplicationExpense editedEntity = getEditedEntity();
        if (!PersistenceHelper.isNew(getEditedEntity())) {
            procAttachmentsDl.setParameter("entityId", editedEntity.getId());
            procAttachmentsDl.load();
            procActionsFragment.initializer().standard().init(PROCESS_CODE, editedEntity);
        }
    }

    @Subscribe("changesBtn")
    private void onChangesBtnClick(Button.ClickEvent event) {
        ApplicationCorrectionHistoryFragment applicationHistoryBrowse = screenBuilders.screen(this)
                .withScreenClass(ApplicationCorrectionHistoryFragment.class)
                .withLaunchMode(OpenMode.NEW_TAB)
                .build();

        applicationHistoryBrowse.setEntity(this.getEditedEntity());
        applicationHistoryBrowse.show();
    }

    @Subscribe("accHistoryBtn")
    private void onAccHistoryBtnClick(Button.ClickEvent event) {

        ApplicationHistoryBrowse applicationHistoryBrowse = screenBuilders.screen(this)
                .withScreenClass(ApplicationHistoryBrowse.class)
                .withLaunchMode(OpenMode.NEW_TAB)
                .build();

        applicationHistoryBrowse.setApplication(this.getEditedEntity());
        applicationHistoryBrowse.show();
    }

    /**
     * Создание редактора
     *
     * @param employee
     * @param app
     * @return
     */
    private ApplicationEditor createEditor(Employee employee, Application app) {
        ApplicationEditor editor = this.metadata.create(ApplicationEditor.class);
        editor.setEmployee(employee);
        editor.setApplication(app);
        editor.setRole(ApplicationResponsibleRole.EDITOR);
        return editor;
    }

    @Subscribe
    private void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        ApplicationExpense appEvent = this.getEditedEntity();
        if (!this.forceSave) {
            ApplicationExpense app = this.appExpenseService.getExpenseWithParams(appEvent.getYear(), appEvent.getPlanArticle(),
                    appEvent.getDivision());
            if (app != null && !app.equals(appEvent)) {
                this.dialogs.createOptionDialog()
                        .withCaption("Подтверждение")
                        .withMessage("Существует заявка на планирование текущих расходов с подобными значениями реквизитов. Хотите открыть её? (текущий экран заявки будет закрыт)")
                        .withActions(
                                new DialogAction(DialogAction.Type.YES, Action.Status.PRIMARY).withHandler(e -> {
                                    this.closeWithDiscard();
                                    this.screenBuilders.editor(ApplicationExpense.class, this)
                                            .editEntity(app)
                                            .withLaunchMode(OpenMode.NEW_WINDOW)
                                            .build()
                                            .show();
                                }),
                                new DialogAction(DialogAction.Type.NO)
                        )
                        .show();
                event.preventCommit();
            }

            if (!this.isNew) {
                if ((this.original.getPlanArticle() == null && appEvent.getPlanArticle() != null) || (this.original.getPlanArticle() != null && appEvent.getPlanArticle() == null)
                        || (this.original.getPlanArticle() != null && appEvent.getPlanArticle() != null &&
                        !this.original.getPlanArticle().equals(appEvent.getPlanArticle()))) {
                    this.dialogs.createOptionDialog()
                            .withCaption("Подтверждение")
                            .withMessage("При изменении статьи ВПО будут удалены все статьи. Продолжить?")
                            .withActions(
                                    new DialogAction(DialogAction.Type.YES, Action.Status.PRIMARY).withHandler(e -> {
                                        this.appExpenseService.removeStages(appEvent);
                                        appEvent.setValue(this.appExpenseService.recalcStagesSum(appEvent));
                                        this.original.setPlanArticle(appEvent.getPlanArticle());
                                        this.forceSave = true;
                                        this.createPlanArticleStages(appEvent);
                                        this.closeWithCommit();
                                    }),
                                    new DialogAction(DialogAction.Type.NO)
                            )
                            .show();
                    event.preventCommit();
                }
            }
        }

        // прикрепляем заявку к плану
        if (appEvent != null && appEvent.getPlanVariantTemplateApplications() != null && appEvent.getPlanVariantTemplateApplications().size() > 0) {
            appEvent.getPlanVariantTemplateApplications().forEach(plan -> {
                plan.setApplication(appEvent);
                event.getDataContext().merge(plan);
            });
        }
    }

    /**
     * Создать статьи заявки на расходы по статье ВПО PlanArticleStage
     * @param app
     */
    private void createPlanArticleStages(ApplicationExpense app) {
        List<PlanArticleStage> stages = this.appExpenseService.getAllPlanArticleStages(app.getPlanArticle());
        stages.stream().forEach(planArticleStage -> {
            CommitContext context = new CommitContext();
            ApplicationStageExpense stage = this.dataManager.create(ApplicationStageExpense.class);
            stage.setApplication(app);
            stage.setName(planArticleStage.getName());
            stage.setPlanArticle(app.getPlanArticle());
            context.addInstanceToCommit(stage);

            ApplicationStageValue applicationStageValue = this.dataManager.create(ApplicationStageValue.class);
            applicationStageValue.setValue(0.0);
            applicationStageValue.setYear(app.getYear());
            applicationStageValue.setApplicationStage(stage);
            stage.setApplicationStageValue(applicationStageValue);
            context.addInstanceToCommit(applicationStageValue);

            ApplicationStageValue v = this.dataManager.create(ApplicationStageValue.class);
            v.setParent(applicationStageValue);
            v.setYear(app.getYear() - 1);
            context.addInstanceToCommit(v);

            for (int i = 0; i < 5; i++) {
                v = this.dataManager.create(ApplicationStageValue.class);
                v.setParent(applicationStageValue);
                v.setYear(app.getYear() + i);
                context.addInstanceToCommit(v);
            }

            ApplicationStageContract contact = this.dataManager.create(ApplicationStageContract.class);
            contact.setStage(stage);
            stage.setApplicationStageContract(contact);
            context.addInstanceToCommit(contact);

            this.dataManager.commit(context);
        });
    }

}
