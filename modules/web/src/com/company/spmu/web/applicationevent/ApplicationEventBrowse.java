package com.company.spmu.web.applicationevent;

import com.company.spmu.entity.*;
import com.company.spmu.enumeration.ApplicationStatus;
import com.company.spmu.enumeration.ViewType;
import com.company.spmu.service.AppEventService;
import com.company.spmu.service.integration.getter.RegistryGetterService;
import com.company.spmu.service.integration.parser.RegistryParserService;
import com.company.spmu.web.applicationstageevent.ApplicationStageEventEdit;
import com.company.spmu.web.bmp.fragment.procactionsfragment.ProcActionsFragment;
import com.company.spmu.web.custom.utils.ResultsByFieldValueUpdateUtils;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.export.ByteArrayDataProvider;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.UserRole;
import com.haulmont.yarg.structure.ReportOutputType;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.validation.constraints.Null;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@UiController("spmu_ApplicationEvent.browse")
@UiDescriptor("application-event-browse.xml")
@LookupComponent("applicationEventsTable")
@LoadDataBeforeShow
public class ApplicationEventBrowse extends StandardLookup<ApplicationEvent> {
    protected static final String PROCESS_CODE = "applicationeventprocessmodel";
    @Inject
    private Filter filter;
    @Inject
    private GroupTable<ApplicationEvent> applicationEventsTable;
    @Inject
    private CollectionLoader<ApplicationEvent> applicationEventsDl;
    @Inject
    private CollectionLoader<ApplicationEvent> allApplicationEventsDl;
    @Inject
    private UserSessionSource userSessionSource;
    @Inject
    private DataManager dataManager;
    @Inject
    private PopupView columnsPopup;
    @Inject
    private CheckBoxGroup columnsList;
    @Inject
    private ProcActionsFragment procActionsFragment;
    @Inject
    private TextField<String> searchField;
    @Inject
    private Notifications notifications;
    @Inject
    private RegistryParserService registryParserService;
    @Inject
    private RegistryGetterService registryGetterService;
    @Inject
    private LookupField<Affilate> affiliateLookupField;
    @Inject
    private LookupField viewTypeField;
    @Inject
    private TreeDataGrid <HierarchyApplicationEvent> treedatagrid;
    @Inject
    private CollectionContainer<ApplicationEvent> applicationEventsDc;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionLoader<ApplicationStageEvent> stagesDl;
    @Inject
    private CollectionContainer<ApplicationStageEvent> stagesDc;
    @Inject
    private CollectionContainer<HierarchyApplicationEvent> hierarchyApplicationsDc;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private AppEventService appEventService;
    @Inject
    private ExportDisplay exportDisplay;

    public void onFilterBtnClick() {
        filter.setVisible(!filter.isVisible());
    }

    public void onSelectAllBtnClick() {
        applicationEventsTable.selectAll();
    }

    public void onCloseBtnClick() {
        this.close(WINDOW_DISCARD_AND_CLOSE_ACTION);
    }

    @Subscribe("affiliateLookupField")
    private void onAffiliateLookupFieldValueChange(HasValue.ValueChangeEvent<Affilate> event) {
        ResultsByFieldValueUpdateUtils.updateResultsByFilterField(applicationEventsDl, "affiliate", event.getValue());
        this.reloadApplicationsTree();
    }

    @Subscribe("responsibleLookupField")
    private void onResponsibleLookupFieldValueChange(HasValue.ValueChangeEvent<Employee> event) {
        List<Application> editorApplications = null;

        if (event.getValue() != null) {
            editorApplications = dataManager.load(Application.class)
                    .query("select i.application from spmu_ApplicationEditor i where i.employee=:selectedEmployee")
                    .parameter("selectedEmployee", event.getValue())
                    .list();
        }

        ResultsByFieldValueUpdateUtils.updateResultsByFilterField(applicationEventsDl, "editorApplications", editorApplications);
        this.reloadApplicationsTree();
    }

    @Subscribe("yearLookupField")
    private void onYearLookupFieldValueChange(HasValue.ValueChangeEvent<ApplicationEvent> event) {
        Integer valueToSet = (event.getValue() != null) ? event.getValue().getYear() : null;
        ResultsByFieldValueUpdateUtils.updateResultsByFilterField(applicationEventsDl, "year", valueToSet);
        this.reloadApplicationsTree();
    }

    @Subscribe("dateStartField")
    private void onDateStartValueChange(HasValue.ValueChangeEvent<Date> event) {
        ResultsByFieldValueUpdateUtils.updateResultsByFilterField(applicationEventsDl, "dateStart", event.getValue());
        this.reloadApplicationsTree();
    }

    @Subscribe("dateEndField")
    private void onDateEndFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        ResultsByFieldValueUpdateUtils.updateResultsByFilterField(applicationEventsDl, "dateEnd", event.getValue());
        this.reloadApplicationsTree();
    }

    @Subscribe("initiatorLookupField")
    private void onInitiatorLookupFieldFieldValueChange(HasValue.ValueChangeEvent<Employee> event) {
        ResultsByFieldValueUpdateUtils.updateResultsByFilterField(applicationEventsDl, "initiatorApplications", event.getValue());
        this.reloadApplicationsTree();
    }

    @Subscribe("correctionCheckBox")
    private void onCorrectionCheckBoxFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        ResultsByFieldValueUpdateUtils.updateResultsByFilterField(applicationEventsDl, "isCorrected", event.getValue());
        this.reloadApplicationsTree();
    }

    @Subscribe("settingsBtn.filterAction")
    private void onSettingsBtnFilterAction(Action.ActionPerformedEvent event) {
        this.filter.setVisible(true);
    }

    @Subscribe
    private void fillColumnsIntoCustomFilterPopupOnInit(InitEvent event) {
        Map<String, Object> collect = this.applicationEventsTable.getColumns().stream()
                .collect(Collectors.toMap(Table.Column::getCaption, Function.identity(),
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
        this.columnsList.setOptionsMap(collect);
    }

    @Subscribe
    private void addCurrentCustomer(InitEvent event) {
        SpmuUser user = (SpmuUser) this.userSessionSource.getUserSession().getUser();
        Employee employee = user.getEmployee();

        boolean indicator = false;
        //проходим по всем ролям пользователя, если нету роли админа тогда другой результат
        for (UserRole role : user.getUserRoles()) {
            if (role.getRole().getName().equalsIgnoreCase("Administrators")) {
                indicator = true;
            }
        }
        if (!indicator) {
            this.allApplicationEventsDl.setParameter("currentAffiliate", user.getAffiliate());
            ResultsByFieldValueUpdateUtils.updateResultsByFilterField(applicationEventsDl, "affiliate", user.getAffiliate());
            ResultsByFieldValueUpdateUtils.updateResultsByFilterField(applicationEventsDl, "currentEmployee", employee);
            this.affiliateLookupField.setValue(user.getAffiliate());
            this.affiliateLookupField.setEditable(false);
        }

        this.reloadApplicationsTree();
    }

    @Subscribe("settingsBtn.columnsAction")
    private void fillSelectedColumnsIntoCustomFilterPopup(Action.ActionPerformedEvent event) {
        this.columnsList.setValue(this.applicationEventsTable.getNotCollapsedColumns());
        this.columnsPopup.setPopupVisible(true);
    }

    @Subscribe("columnsList")
    private void onColumnSelectedChangeInCustomFilterPopup(HasValue.ValueChangeEvent event) {
        Collection lookupSelectedItems = this.columnsList.getLookupSelectedItems();
        this.applicationEventsTable.getColumns()
                .forEach(currentColumn -> currentColumn.setCollapsed(!lookupSelectedItems.contains(currentColumn)));
    }


    @Subscribe("applicationEventsTable")
    private void onRecordSelect(Table.SelectionEvent<ApplicationEvent> event) {
        Set<ApplicationEvent> selected = event.getSelected();
        boolean isShowProcActions = selected.size() == 1;
        if (isShowProcActions) {
            selected.forEach(selectedEntity -> procActionsFragment.initializer()
                    .standard()
                    .init(PROCESS_CODE, selectedEntity));

        }
        procActionsFragment.getFragment().setVisible(isShowProcActions);
    }

    @Subscribe("applicationEventsTable.search")
    private void onApplicationEventsTableSearch(Action.ActionPerformedEvent event) {
        String search = this.searchField.getValue();
        if (StringUtils.isNotEmpty(search)) {
            search = "%" + search + "%";
        } else {
            search = null;
        }
        ResultsByFieldValueUpdateUtils.updateResultsByFilterField(this.applicationEventsDl, "s1", search);
        ResultsByFieldValueUpdateUtils.updateResultsByFilterField(this.applicationEventsDl, "s2", search);
        ResultsByFieldValueUpdateUtils.updateResultsByFilterField(this.applicationEventsDl, "s3", search);
        this.reloadApplicationsTree();
    }

    @Subscribe
    public void importApplicationEvent() {
        registryGetterService.getData();
        notifications.create().withCaption("Заявки на мероприятия импортированы").show();
    }

    @Subscribe
    public void createRegistry() {
        registryParserService.createRegistry();
        notifications.create().withCaption("Создан новый реестр в 1С").show();
    }

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        this.viewTypeField.setValue(ViewType.HIERARCHY);

        this.treedatagrid.addRowStyleProvider(hierarchyApplicationEvent -> {
            ApplicationStatus status;
            try {
                status = hierarchyApplicationEvent.getApp().getStatus();
            }
            catch (NullPointerException e) {
                return null;
            }
            if (status == null) return null;
            switch (status) {
                case FINALLY_REJECTED_RA:
                    return "finally_rejected_ra";

                case DOWNLOAD_IN_YMIP:
                    return "download_in_ymip";

                case REJECTED_RA_WITH_A_NOTE_FOR_LATER_ADJUSTMENT:
                    return "rejected_ra_with_a_note_for_later_adjustment";

                default:
                    return null;
            }

        });

        this.applicationEventsTable.setStyleProvider((entity, property) -> {
            //if (StringUtils.equals("name", property)) {
                switch (entity.getStatus()) {
                    case FINALLY_REJECTED_RA:
                        return "finally_rejected_ra";

                    case DOWNLOAD_IN_YMIP:
                        return "download_in_ymip";

                    case REJECTED_RA_WITH_A_NOTE_FOR_LATER_ADJUSTMENT:
                        return "rejected_ra_with_a_note_for_later_adjustment";

                    default:
                        return null;
                }

            //}

           // return null;
        });

    }


    @Subscribe("viewTypeField")
    private void onViewTypeFieldValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue().equals(ViewType.HIERARCHY)) {
            this.treedatagrid.setVisible(true);
            this.applicationEventsTable.setVisible(false);
        } else {
            this.treedatagrid.setVisible(false);
            this.applicationEventsTable.setVisible(true);
        }
    }

    private void reloadApplicationsTree() {
        this.applicationEventsDl.load();
        List<ApplicationEvent> applist = this.applicationEventsDc.getItems();
        List<HierarchyApplicationEvent> hlist = new ArrayList<>();
        Long numId = 1L;
        for (ApplicationEvent a : applist) {
            HierarchyApplicationEvent h = this.metadata.create(HierarchyApplicationEvent.class);
            h.setApp(a);
            h.setStage(null);
            h.setId(numId);
            h.setName(a.getName());
            h.setAffiliate(a.getAffiliate() != null ? a.getAffiliate().getName() : "");
            h.setPlanArticle(a.getPlanArticle() != null ? a.getPlanArticle().getName() : "");
            h.setDateStart(a.getDateStart());
            h.setDateEnd(a.getDateEnd());
            h.setValue(a.getValue());
            numId++;
            hlist.add(h);

            this.stagesDl.setQuery("select e from spmu_ApplicationStageEvent e where e.application=:a");
            this.stagesDl.setParameter("a", a);
            this.stagesDl.load();

            List<ApplicationStageEvent> stages = this.stagesDc.getItems();
            for (ApplicationStageEvent ar : stages) {
                HierarchyApplicationEvent h2 = this.metadata.create(HierarchyApplicationEvent.class);
                h2.setApp(null);
                h2.setStage(ar);
                h2.setId(numId);
                h2.setParent(h);
                h2.setName(ar.getName());
                h2.setAffiliate(ar.getAffiliate() != null ? ar.getAffiliate().getName() : "");
                h2.setPlanArticle(ar.getPlanArticle() != null ? ar.getPlanArticle().getName() : "");
                h2.setDateStart(ar.getStart());
                h2.setDateEnd(ar.getEnd());
                h2.setValue(ar.getValue());
                numId++;
                hlist.add(h2);
            }
        }

        this.hierarchyApplicationsDc.setItems(hlist);
    }

    @Subscribe("treedatagrid.edit")
    private void onTreedatagridEdit(Action.ActionPerformedEvent event) {
        HierarchyApplicationEvent sel = (HierarchyApplicationEvent) this.treedatagrid.getSingleSelected();
        if (sel == null) return;
        if (sel.getApp() != null) {
            ApplicationEvent edited = this.dataManager.load(ApplicationEvent.class)
                    .id(sel.getApp().getId()).view("applicationEvent-view-full").one();
            this.screenBuilders.editor(ApplicationEvent.class, this)
                    .editEntity(edited)
                    .withLaunchMode(OpenMode.NEW_WINDOW)
                    .withScreenClass(ApplicationEventEdit.class)
                    .withAfterCloseListener(applicationIncomeEditAfterScreenCloseEvent -> {
                        this.reloadApplicationsTree();
                    })
                    .build()
                    .show();
        } else if (sel.getStage() != null) {
            ApplicationStageEvent edited = this.dataManager.load(ApplicationStageEvent.class)
                    .id(sel.getStage().getId()).view("applicationStageEvent-edit").one();
            screenBuilders.editor(ApplicationStageEvent.class, this)
                    .editEntity(edited)
                    .withLaunchMode(OpenMode.NEW_WINDOW)
                    .withScreenClass(ApplicationStageEventEdit.class)
                    .withAfterCloseListener(applicationStageIncomeEditAfterScreenCloseEvent -> {
                        this.reloadApplicationsTree();
                    })
                    .build()
                    .show();
        }
    }

    @Subscribe("exportButton.toPdf")
    private void onExportButtonToPdf(Action.ActionPerformedEvent event) {
        byte[] bytes;
        try {
            bytes = this.appEventService.exportReport(this.applicationEventsDc.getItems(), ReportOutputType.pdf);
            this.exportDisplay.show(new ByteArrayDataProvider(bytes), "appevents.pdf", ExportFormat.PDF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
