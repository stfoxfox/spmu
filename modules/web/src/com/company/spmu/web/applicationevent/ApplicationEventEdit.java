package com.company.spmu.web.applicationevent;

import com.company.spmu.entity.*;
import com.company.spmu.service.AppIncomeService;
import com.company.spmu.web.applicationfiledescriptor.ApplicationFileDescriptorEdit;
import com.company.spmu.web.applicationhistory.ApplicationHistoryBrowse;
import com.company.spmu.web.applicationlink.ApplicationLinkBrowseFragment;
import com.company.spmu.web.applicationstageevent.ApplicationStageEventBrowserFragment;
import com.company.spmu.web.bmp.fragment.procactionsfragment.ProcActionsFragment;
import com.company.spmu.web.correctionhistory.ApplicationCorrectionHistoryFragment;
import com.haulmont.bpm.entity.ProcAttachment;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.model.impl.InstanceLoaderImpl;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@UiController("spmu_ApplicationEvent.edit")
@UiDescriptor("application-event-edit.xml")
@EditedEntityContainer("applicationEventDc")
@LoadDataBeforeShow
public class ApplicationEventEdit extends StandardEditor<ApplicationEvent> {
    protected static final String PROCESS_CODE = "applicationeventprocessmodel";
    private int numDetail = 1;
    private int numF = 1;
    private Employee employee = null;
    private List<ApplicationDetail> detailsList = null;
    boolean isNew = false;

    @Inject
    protected UserSessionSource userSessionSource;
    @Inject
    private InstanceContainer<ApplicationEvent> applicationEventDc;
    @Inject
    private InstanceLoaderImpl<ApplicationEvent> applicationEventDl;
    @Inject
    private Button printBtn;
    //@Inject
    // private Button editBtn;
    //@Inject
    // private Button corReqBtn;
    //  @Inject
    //private Button gantBtn;
    @Inject
    private Button accHistoryBtn;
    @Inject
    private Button stagesBtn;
    // @Inject
    // private Button createCorBtn;
    // @Inject
    // private Button createStageBtn;
    // @Inject
    // private Button cancelAcceptBtn;
    // @Inject
    // private Button toArchiveBtn;
    @Inject
    private CollectionLoader<ProcAttachment> procAttachmentsDl;
    @Inject
    private ProcActionsFragment procActionsFragment;
    @Inject
    private TextField<String> valueField;
    @Inject
    private CollectionLoader<ApplicationDetail> detailsLc;
    @Inject
    private TextField<String> curatorPost;
    @Inject
    private TextField<String> managerPost;
    @Inject
    private LookupField<Employee> initiator;
    @Inject
    private AppIncomeService appIncomeService;
    @Inject
    private CheckBox isGge;
    @Inject
    private CssLayout divGge;
    @Inject
    private CssLayout divPsd;
    @Inject
    private CheckBox isPsd;
    @Inject
    private Table<ApplicationDetail> tablePsd;
    @Inject
    private UiComponents uiComponents;
    @Inject
    private CollectionContainer<ApplicationDetail> detailsDc;
    @Inject
    private LookupPickerField<Employee> curatorField;
    @Inject
    private LookupPickerField<Employee> managerField;
    @Inject
    private Metadata metadata;
    @Inject
    private EntityStates entityStates;
    @Inject
    private Fragment stagebrowser;
    @Inject
    private Fragment applicationLink;
    @Named("subTabs.subsubTab4")
    private VBoxLayout subsubTab4;
    @Inject
    private TextField<Double> limitsField;
    @Inject
    private Table<ApplicationFileDescriptor> filesTable;
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionLoader<ApplicationFileDescriptor> applicationFileDescriptorsLc;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private ExportDisplay exportDisplay;
    @Inject
    private FileUploadField uploadField;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private CollectionContainer<ApplicationFileDescriptor> applicationFileDescriptorsDc;
    @Inject
    private DateField<Date> dateStartField;
    @Inject
    private DateField<Date> dateEndField;
    @Inject
    protected Label labelTitle;
    @Inject
    private TabSheet tabMain;

    @Subscribe("isPsd")
    private void onIsPsdValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        this.togglePsd(event.getValue());
        if (event.isUserOriginated() && event.getValue() && this.tablePsd.getItems().size() == 0) {
            // Создаём строки таблицы только если пользователь нажал галочку
            this.createDetailsTablePre(this.getEditedEntity());
        }
    }

    private void togglePsd(Boolean on) {
        this.divPsd.setVisible(on);
        this.tablePsd.setVisible(on);
    }

    @Subscribe("isGge")
    private void onIsGgeValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        this.toggleGge(event.getValue());
    }

    private void toggleGge(boolean on) {
        this.divGge.setVisible(on);
    }

    @Subscribe
    public void onInitEntity(InitEntityEvent<ApplicationEvent> event) {
        this.isNew = true;
        ApplicationEvent app = event.getEntity();

        // Заполняем дефолтные значения для новой заявки
        app.setResponsible(this.employee);

        // Отключаем лишние кнопки для новой заявки
        this.printBtn.setEnabled(false);
        /*this.editBtn.setEnabled(false);
       this.corReqBtn.setEnabled(false);
       this.gantBtn.setEnabled(false);
       */
        this.accHistoryBtn.setEnabled(false);
        this.stagesBtn.setEnabled(false);
        /*
        this.createCorBtn.setEnabled(false);
        this.createStageBtn.setEnabled(false);
        this.cancelAcceptBtn.setEnabled(false);
        this.toArchiveBtn.setEnabled(false);
        */
        this.subsubTab4.setVisible(false);
        this.uploadField.setEnabled(false);

        // Добавляем текущего пользователя в редакторы
        ApplicationEditor editor = this.createEditor(this.employee, app);
        List<ApplicationEditor> listEditors = new ArrayList<>();
        listEditors.add(editor);
        app.setApplicationResponsibleEditors(listEditors);
    }

    /**
     * Создание пустых строк такблицы доп. атрибутов заявки по текущей заявке
     *
     * @param app
     */
    private void createDetailsTablePre(Application app) {
        List<ApplicationDetailType> typesList = this.appIncomeService.getAllDetailTypes();
        this.detailsList = new ArrayList<>();
        typesList.stream().forEach(applicationDetailType -> {
            ApplicationDetail d = this.metadata.create(ApplicationDetail.class);
            d.setApplication(app);
            d.setType(applicationDetailType);
            d.setValue1("");
            d.setValue2("");
            d.setValue3("");
            this.detailsList.add(d);
        });
        this.detailsDc.setItems(this.detailsList);
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    private void onPreCommit(DataContext.PreCommitEvent event) {
        boolean found = false;
        ApplicationEvent appEvent = this.getEditedEntity();

        // Сохранение списка редакторов
        List<?> editors = applicationEventDc.getItem().getValue("applicationResponsibleEditors");
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

        // Сохраняем ранее созданную таблицу доп. атрибутов
        if (this.detailsList != null && this.detailsList.size() > 0) {
            this.detailsList.stream().forEach(applicationDetail -> {
                event.getModifiedInstances().add(applicationDetail);
            });
        }

        // прикрепляем заявку к плану
        if (appEvent != null && appEvent.getPlanVariantTemplateApplications() != null && appEvent.getPlanVariantTemplateApplications().size() > 0) {
            appEvent.getPlanVariantTemplateApplications().forEach(plan -> {
                plan.setApplication(appEvent);
                event.getModifiedInstances().add(plan);
            });
        }
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    private void onPostCommit(DataContext.PostCommitEvent event) {
        ApplicationEvent appEvent = this.getEditedEntity();

        // Сохранение инициатора
        Employee employee = this.initiator.getValue();
        if (employee != null) {
            this.appIncomeService.saveInitiator(appEvent, employee);
        }

        // Сохранение "Ответственный за мероприятие в ГД"
        employee = this.curatorField.getValue();
        if (employee != null) {
            this.appIncomeService.saveCurator(appEvent, employee);
        }

        // Сохранение "Ответственный за мероприятие в филиале"
        employee = this.managerField.getValue();
        if (employee != null) {
            this.appIncomeService.saveManager(appEvent, employee);
        }
    }

    @Subscribe
    private void initProcess(BeforeShowEvent event) {
        ApplicationEvent editedEntity = getEditedEntity();
        if (!PersistenceHelper.isNew(editedEntity)) {
            procAttachmentsDl.setParameter("entityId", editedEntity.getId());
            procAttachmentsDl.load();
            procActionsFragment.initializer().standard().init(PROCESS_CODE, editedEntity);
        }
    }

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        ApplicationEvent ae = this.getEditedEntity();
        if (!PersistenceHelper.isNew(ae)) {
            // Заголовки
            String title = "Заявка на мероприятие";
            title += ((ae.getCode() != null ? " №" + ae.getCode() : "")+(ae.getName() != null ? "\"" + ae.getName() + "\"" : ""));
            labelTitle.setValue(title);
            this.getWindow().setCaption("Заявка на мероприятие" + (ae.getCode() != null ? " №" + ae.getCode() : ""));
            this.tabMain.getTab("subTab1").setCaption("Заявка на мероприятие" + (ae.getCode() != null ? " №" + ae.getCode() : ""));
            this.tabMain.getTab("subTab2").setCaption("Этапы заявки на мероприятие" + (ae.getCode() != null ? " №" + ae.getCode() : ""));
        }
        else{
            String title = "Создание заявки на мероприятие";
            labelTitle.setValue(title);
        }
        ApplicationEvent editedEntity = this.getEditedEntity();
        this.valueField.setValue("В тыс.руб.");
        this.toggleGge(this.isGge.isChecked());
        this.togglePsd(this.isPsd.isChecked());

        if (this.isNew) {
            this.detailsLc.setParameter("a", null);
            this.applicationFileDescriptorsLc.setParameter("app", null);
        } else {
            this.detailsLc.setParameter("a", editedEntity);
            this.applicationFileDescriptorsLc.setParameter("app", editedEntity);
        }
        this.numDetail = 1;
        this.numF = 1;
    }

    @Subscribe
    private void onInit(InitEvent event) {
        SpmuUser user = (SpmuUser) this.userSessionSource.getUserSession().getUser();
        if (user != null) {
            this.employee = user.getEmployee();
        }

        this.uploadField.addFileUploadSucceedListener(uploadSucceedEvent -> {
            File file = this.fileUploadingAPI.getFile(this.uploadField.getFileId());
            if (file != null) {
                FileDescriptor fd = this.uploadField.getFileDescriptor();
                try {
                    this.fileUploadingAPI.putFileIntoStorage(this.uploadField.getFileId(), fd);
                } catch (FileStorageException e) {
                    throw new RuntimeException("Error saving file to FileStorage", e);
                }
                this.dataManager.commit(fd);
                ApplicationFileDescriptor aFile = this.metadata.create(ApplicationFileDescriptor.class);
                aFile.setFile(fd);
                aFile.setApplication(this.getEditedEntity());
                this.dataManager.commit(aFile);
                this.applicationFileDescriptorsLc.load();
            }
        });

        this.uploadField.addFileUploadErrorListener(uploadErrorEvent -> {
            System.out.println(uploadErrorEvent.toString());
        });
    }

    public Component generateDetailNum(Entity entity) {
        Label label = this.uiComponents.create(Label.class);
        label.setValue(this.numDetail);
        this.numDetail++;
        if (this.numDetail > this.detailsDc.getMutableItems().size()) {
            this.numDetail = 1;
        }
        return label;
    }

    @Subscribe
    private void onAfterShow(AfterShowEvent event) {
        ApplicationEvent ae = this.getEditedEntity();

        // Установка значения поле Инициатор
        ApplicationResponsible ai = ae.getApplicationResponsibleInitiator();
        if (ai != null) {
            this.initiator.setValue(ai.getEmployee());
        }

        // Установка значения поле "Ответственный за мероприятие в ГД"
        ai = ae.getApplicationResponsibleCurator();
        if (ai != null) {
            this.curatorField.setValue(ai.getEmployee());
        }

        // Установка значения поле "Ответственный за мероприятие в филиале"
        ai = ae.getApplicationResponsibleManager();
        if (ai != null) {
            this.managerField.setValue(ai.getEmployee());
        }

        // Устанавливаем родительский Application для списка статей и отображаем список статей во вкладке
        ApplicationStageEventBrowserFragment stageBrowser = (ApplicationStageEventBrowserFragment) this.stagebrowser.getFrameOwner();
        stageBrowser.setParentApp(this.getEditedEntity());

        //Устанавливаем родительский Application для списка связей
        ApplicationLinkBrowseFragment linkBrowser = (ApplicationLinkBrowseFragment) this.applicationLink.getFrameOwner();
        linkBrowser.setParentApp(this.getEditedEntity());
    }

    @Subscribe("curatorField")
    private void onCuratorFieldValueChange(HasValue.ValueChangeEvent<Employee> event) {
        Employee e = event.getValue();
        if (e == null) {
            this.curatorPost.setValue("");
        } else {
            Position p = e.getPosition();
            if (p == null) {
                this.curatorPost.setValue("");
            } else {
                this.curatorPost.setValue(p.getName());
            }
        }
    }

    @Subscribe("managerField")
    private void onManagerFieldValueChange(HasValue.ValueChangeEvent<Employee> event) {
        Employee e = event.getValue();
        if (e == null) {
            this.managerPost.setValue("");
        } else {
            Position p = e.getPosition();
            if (p == null) {
                this.managerPost.setValue("");
            } else {
                this.managerPost.setValue(p.getName());
            }
        }
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
        return editor;
    }

    public void saveLimits(Double sum) {
        try {
            ApplicationEvent e = this.dataManager.load(ApplicationEvent.class).id(this.getEditedEntity().getId()).one();
            if (e != null) {
                e.setValue(sum);
                this.dataManager.commit(e);
            }
            this.limitsField.setValue(sum);
        } catch (IllegalStateException ex) {
        }
    }

    public Component fileNumGenerate(Entity entity) {
        Label label = this.uiComponents.create(Label.class);
        label.setValue(this.numF);
        this.numF++;
        if (this.numF > this.applicationFileDescriptorsDc.getMutableItems().size()) {
            this.numF = 1;
        }
        return label;
    }

    @Subscribe("file_delete")
    private void onFile_delete(Action.ActionPerformedEvent event) {
        ApplicationFileDescriptor selected = this.filesTable.getSingleSelected();
        this.dataManager.remove(selected);
        this.applicationFileDescriptorsLc.load();
    }

    @Subscribe("file_open")
    private void onFile_open(Action.ActionPerformedEvent event) {
        ApplicationFileDescriptor selected = this.filesTable.getSingleSelected();
        if (selected != null) {
            this.screenBuilders.editor(ApplicationFileDescriptor.class, this)
                    .withScreenClass(ApplicationFileDescriptorEdit.class).newEntity(selected)
                    .withLaunchMode(OpenMode.DIALOG)
                    .withAfterCloseListener(applicationFileDescriptorEditAfterScreenCloseEvent -> {
                        this.applicationFileDescriptorsLc.load();
                    })
                    .build()
                    .show();
        }
    }

    @Subscribe("file_save")
    private void onFile_save(Action.ActionPerformedEvent event) {
        ApplicationFileDescriptor selected = this.filesTable.getSingleSelected();
        if (selected != null) {
            this.exportDisplay.show(selected.getFile(), ExportFormat.OCTET_STREAM);
        }
    }

    public void saveDates(Date dateStart, Date dateEnd) {
        ApplicationEvent e = this.dataManager.load(ApplicationEvent.class).id(this.getEditedEntity().getId()).one();
        if (e != null) {
            e.setDateStart(dateStart);
            e.setDateEnd(dateEnd);
            this.dataManager.commit(e);
        }
        this.dateStartField.setValue(dateStart);
        this.dateEndField.setValue(dateEnd);
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

    @Subscribe("changesBtn")
    private void onChangesBtnClick(Button.ClickEvent event) {
        ApplicationCorrectionHistoryFragment applicationHistoryBrowse = screenBuilders.screen(this)
                .withScreenClass(ApplicationCorrectionHistoryFragment.class)
                .withLaunchMode(OpenMode.NEW_TAB)
                .build();

        applicationHistoryBrowse.setEntity(this.getEditedEntity());
        applicationHistoryBrowse.show();
    }

}
