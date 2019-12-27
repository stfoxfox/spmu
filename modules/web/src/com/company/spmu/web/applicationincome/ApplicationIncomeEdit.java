package com.company.spmu.web.applicationincome;

import com.company.spmu.entity.*;
import com.company.spmu.enumeration.ApplicationResponsibleRole;
import com.company.spmu.service.AppIncomeService;
import com.company.spmu.web.applicationhistory.ApplicationHistoryBrowse;
import com.company.spmu.web.applicationstageincome.ApplicationStageIncomeBrowseFragment;
import com.company.spmu.web.bmp.fragment.procactionsfragment.ProcActionsFragment;
import com.company.spmu.web.correctionhistory.ApplicationCorrectionHistoryFragment;
import com.haulmont.bpm.entity.ProcAttachment;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.PersistenceHelper;
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


@UiController("spmu_ApplicationIncome.edit")
@UiDescriptor("application-income-edit.xml")
@EditedEntityContainer("applicationIncomeDc")
@LoadDataBeforeShow
public class ApplicationIncomeEdit extends StandardEditor<ApplicationIncome> {
    protected static final String PROCESS_CODE = "applicationincomeprocessmodel";
    private Employee employee = null;
    @Inject
    protected Button save;
    //@Inject
    //private Button print;

    @Inject
    public InstanceContainer applicationIncomeDc;

    @Inject
    protected Label titleLabel;

    @Inject
    private DataManager dataManager;

    @Inject
    private Button changesBtn;
    @Inject
    private Button accHistoryBtn;
    //@Inject
    //private Button createCorBtn;
    //@Inject
    //private Button toArchive;
    //@Inject
    //private Button cancelAcceptBtn;
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
    private AppIncomeService appIncomeService;
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
    private EntityStates entityStates;
    @Inject
    private Metadata metadata;
    @Inject
    private TextField<String> planTemplateField;

    /**
     * проверка на дубли редакторов
     * */
    @Subscribe("editors")
    private void onEditorsValueChange(HasValue.ValueChangeEvent<Collection<ApplicationEditor>> collectionValueChangeEvent) {
        List<ApplicationEditor> applicationDefautlEditors = ((ApplicationIncome) applicationIncomeDc.getItem()).getApplicationResponsibleEditors();
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
        ((ApplicationIncome) applicationIncomeDc.getItem()).setApplicationResponsibleEditors(applicationDefautlEditors);
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
    public void onInitEntity(InitEntityEvent<ApplicationIncome> event) {
        // Дефортные значения для новой записи

        event.getEntity().setResponsible(this.employee);
        ApplicationIncome app = event.getEntity();

        // Скрываем кнопки для новой записи
        //this.print.setEnabled(false);
        this.changesBtn.setEnabled(false);
        this.accHistoryBtn.setEnabled(false);
        //this.createCorBtn.setEnabled(false);
        //this.toArchive.setEnabled(false);
        //this.cancelAcceptBtn.setEnabled(false);

        // Добавляем текущего пользователя в редакторы
        ApplicationEditor editor = this.createEditor(this.employee, app);
        List<ApplicationEditor> listEditors = new ArrayList<>();
        listEditors.add(editor);
        app.setApplicationResponsibleEditors(listEditors);
    }

    @Subscribe
    private void onAfterShow(AfterShowEvent event) {
        // Установка значения поле Инициатор
        ApplicationResponsible ai = this.getEditedEntity().getApplicationResponsibleInitiator();
        if (ai != null) {
            this.initiator.setValue(ai.getEmployee());
        }

        // Устанавливаем родительский Application для списка статей и отображаем список статей во вкладке
        ApplicationStageIncomeBrowseFragment stageBrowser = (ApplicationStageIncomeBrowseFragment) this.stagebrowser.getFrameOwner();
        stageBrowser.setParentApp(this.getEditedEntity());
    }

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        ApplicationIncome ai = this.getEditedEntity();
        this.checkAffiliateField();
        if (!PersistenceHelper.isNew(ai)) {
            // Заголовки
            String title = "Заявка на доходы";
            title += ((ai.getCode() != null ? " №" + ai.getCode() : "") +(ai.getName() != null ? " \"" + ai.getName() +"\"": ""));
            titleLabel.setValue(title);
            this.getWindow().setCaption("Заявка на доходы" + (ai.getCode() != null ? " №" + ai.getCode() : ""));
            this.tabs.getTab("editortab").setCaption("Заявка на доходы" + (ai.getCode() != null ? " №" + ai.getCode() : ""));
            this.tabs.getTab("articles_list").setCaption("Статьи заявки на доходы" + (ai.getCode() != null ? " №" + ai.getCode() : "") );
        } else {
            String title = "Создание заявки на доходы";
            titleLabel.setValue(title);
        }

        this.valueField.setValue("В тыс.руб.");

        // Поле "План"
        if (ai != null && ai.getPlanVariantTemplateApplications() != null && !ai.getPlanVariantTemplateApplications().isEmpty()
                && ai.getPlanVariantTemplateApplications().get(0).getPlanVariantTemplate() != null)
        {
            this.planTemplateField.setValue(ai.getPlanVariantTemplateApplications().get(0).getPlanVariantTemplate().getPlanVariant().getName());
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
    protected void onAfterClose(AfterCloseEvent event) {
        save.addClickListener(clickEvent -> {
            List<Employee> editors = applicationIncomeDc.getItem().getValue("applicationResponsibleEditors");
            int i = 0;
            while (i < editors.size()) {
                if(editors.get(i) instanceof Employee) {
                    ApplicationEditor editor = new ApplicationEditor();
                    editor.setEmployee(editors.get(i));
                    editor.setApplication(this.getEditedEntity());
                    dataManager.commit(editor);
                }
                i++;
            }
        });
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
        ApplicationIncome applicationIncome = this.getEditedEntity();

        // Сохранение инициатора
        Employee employee = this.initiator.getValue();
        if (employee != null) {
            this.appIncomeService.saveInitiator(applicationIncome, employee);
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
        ApplicationIncome appEvent = this.getEditedEntity();
        List<?> editors = applicationIncomeDc.getItem().getValue("applicationResponsibleEditors");
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

        // прикрепляем заявку к плану
        if (appEvent != null && appEvent.getPlanVariantTemplateApplications() != null && appEvent.getPlanVariantTemplateApplications().size() > 0) {
            appEvent.getPlanVariantTemplateApplications().forEach(plan -> {
                plan.setApplication(appEvent);
                event.getModifiedInstances().add(plan);
            });
        }
    }

    @Subscribe
    private void initProcess(AfterShowEvent event) {
        ApplicationIncome editedEntity = getEditedEntity();
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

}
