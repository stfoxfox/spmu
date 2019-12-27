package com.company.spmu.web.applicationincome;

import com.company.spmu.entity.*;
import com.company.spmu.enumeration.ViewType;
import com.company.spmu.service.AppIncomeService;
import com.company.spmu.web.applicationstageincome.ApplicationStageIncomeEdit;
import com.company.spmu.web.bmp.fragment.procactionsfragment.ProcActionsFragment;
import com.company.spmu.web.custom.utils.ResultsByFieldValueUpdateUtils;
import com.haulmont.cuba.core.global.*;
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

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

@UiController("spmu_ApplicationIncome.browse")
@UiDescriptor("application-income-browse.xml")
@LookupComponent("applicationIncomesTable")
@LoadDataBeforeShow
public class ApplicationIncomeBrowse extends StandardLookup<ApplicationIncome> {
    protected static final String PROCESS_CODE = "applicationincomeprocessmodel";
    @Inject
    private Filter filter;
    @Inject
    private CollectionLoader<ApplicationIncome> applicationIncomesDl;
    @Inject
    private UserSessionSource userSessionSource;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private CollectionLoader<Division> divisionsDl;
    @Inject
    private PopupView columnsPopup;
    @Inject
    private CheckBoxGroup columnsList;
    @Inject
    private ProcActionsFragment procActionsFragment;
    @Inject
    private CollectionContainer<ApplicationIncome> applicationIncomesDc;
    @Inject
    private AppIncomeService appIncomeService;
    @Inject
    private ExportDisplay exportDisplay;
    @Inject
    private TextField<String> searchField;
    @Inject
    private CollectionLoader<Affilate> affiliateDl;
    @Inject
    private LookupField<Affilate> affiliateLookupField;
    @Inject
    private CollectionContainer<HierarchyApplicationIncome> hierarchyApplicationsDc;
    @Inject
    private CollectionLoader<ApplicationStageIncome> stagesDl;
    @Inject
    private CollectionContainer<ApplicationStageIncome> stagesDc;
    @Inject
    private Metadata metadata;
    @Inject
    private DataGrid<ApplicationIncome> applicationIncomesTable;
    @Inject
    private DataManager dataManager;
    @Inject
    private TreeDataGrid<HierarchyApplicationIncome> treedatagrid;
    @Inject
    private LookupField viewTypeField;

    @Subscribe("affiliateLookupField")
    private void onAffiliateLookupFieldValueChange(HasValue.ValueChangeEvent<Affilate> event) {
        ResultsByFieldValueUpdateUtils.setFilterField(applicationIncomesDl, "affiliate", event.getValue());
        ResultsByFieldValueUpdateUtils.setFilterField(divisionsDl, "affiliate", event.getValue());
        this.reloadApplicationsTree();
    }

    @Subscribe("divisionLookupField")
    private void onDivisionLookupFieldValueChange(HasValue.ValueChangeEvent<Division> event) {
        ResultsByFieldValueUpdateUtils.setFilterField(applicationIncomesDl, "division", event.getValue());
        this.reloadApplicationsTree();
    }

    @Subscribe("articleLookupField")
    private void onArticleLookupFieldValueChange(HasValue.ValueChangeEvent<PlanArticle> event) {
        ResultsByFieldValueUpdateUtils.setFilterField(applicationIncomesDl, "planArticle", event.getValue());
        this.reloadApplicationsTree();
    }

    @Subscribe("initiatorLookupField")
    private void onInitiatorLookupFieldValueChange(HasValue.ValueChangeEvent<Employee> event) {
        ResultsByFieldValueUpdateUtils.setFilterField(applicationIncomesDl, "applicationResponsibleInitiator", event.getValue());
        this.reloadApplicationsTree();
    }

    @Subscribe("correctionLookup")
    private void onCorrectionLookupValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        ResultsByFieldValueUpdateUtils.setFilterField(applicationIncomesDl, "change", event.getValue());
        this.reloadApplicationsTree();
    }

    @Subscribe("statusTypeField")
    private void onStatusTypeFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        ResultsByFieldValueUpdateUtils.setFilterField(applicationIncomesDl, "status", event.getValue());
        this.reloadApplicationsTree();
    }

    @Subscribe
    private void onInit(InitEvent event) {
        loadApplications();
        initColumnsMenu();
        setUnvisableColumn();

        applicationIncomesTable.addItemClickListener(applicationIncomeItemClickEvent -> {
            if(applicationIncomeItemClickEvent.getItem() != null) {
                if (applicationIncomeItemClickEvent.getColumnId().equals("name") || applicationIncomeItemClickEvent.getColumnId().equals("code")) {
                    ApplicationIncome applicationIncome = applicationIncomeItemClickEvent.getItem();
                    screenBuilders.editor(ApplicationIncome.class, this)
                            .editEntity(applicationIncome)
                            .withLaunchMode(OpenMode.NEW_WINDOW)
                            .build()
                            .show();
                } else {
                    applicationIncomesTable.setSelected(applicationIncomeItemClickEvent.getItem());
                }
            }
        });
    }

    private void loadApplications() {
        SpmuUser user = (SpmuUser) this.userSessionSource.getUserSession().getUser();
        boolean indicator = false;
        //проходим по всем ролям пользователя, если нету роли админа тогда другой результат
        for (UserRole role : user.getUserRoles()){
            if(role.getRole().getName().equalsIgnoreCase("Administrators")){
                indicator = true;
            }
        }
        if (!indicator) {
            ResultsByFieldValueUpdateUtils.setFilterField(applicationIncomesDl, "affiliate", user.getAffiliate());
            ResultsByFieldValueUpdateUtils.updateResultsByFilterField(affiliateDl, "affiliate", user.getAffiliate());
            ResultsByFieldValueUpdateUtils.updateResultsByFilterField(divisionsDl, "affiliate", user.getAffiliate());
            this.affiliateLookupField.setValue(user.getAffiliate());
            this.affiliateLookupField.setEditable(false);
        }

        this.reloadApplicationsTree();
    }

    /**
     * Самодельное меню выбора видимости колонок
     */
    private void initColumnsMenu() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("Наименование", 0);
        map.put("№ Заявки", 1);
        map.put("Подразделение", 2);
        map.put("Статья ВПО", 3);
        map.put("Начало", 4);
        map.put("Окончание", 5);
        map.put("Стоимость", 6);
        this.columnsList.setOptionsMap(map);
    }

    private void setUnvisableColumn() {
        applicationIncomesTable.getColumn("status").setVisible(false);
        applicationIncomesTable.getColumn("plan").setVisible(false);
        applicationIncomesTable.getColumn("type").setVisible(false);
        applicationIncomesTable.getColumn("change").setVisible(false);
        applicationIncomesTable.getColumn("kind").setVisible(false);
        applicationIncomesTable.getColumn("active").setVisible(false);
        applicationIncomesTable.getColumn("date").setVisible(false);
        applicationIncomesTable.getColumn("description").setVisible(false);
        applicationIncomesTable.getColumn("order").setVisible(false);
        applicationIncomesTable.getColumn("layout").setVisible(false);
        applicationIncomesTable.getColumn("assignment").setVisible(false);
        applicationIncomesTable.getColumn("proof").setVisible(false);
        applicationIncomesTable.getColumn("year").setVisible(false);
    }

    @Subscribe("applicationIncomesTable.edit")
    private void onApplicationIncomesTableEdit(Action.ActionPerformedEvent event) {
        screenBuilders.editor(ApplicationIncome.class, this)
                .editEntity(this.applicationIncomesTable.getSingleSelected())
                .withLaunchMode(OpenMode.NEW_WINDOW)
                .build()
                .show();
    }

    public void onFilterBtnClick() {
        filter.setVisible(!filter.isVisible());
    }

    public void onSelectBtnClick() {
        if (applicationIncomesTable.getSelectionMode() == DataGrid.SelectionMode.MULTI_CHECK) {
            applicationIncomesTable.setSelectionMode(DataGrid.SelectionMode.SINGLE);
        } else {
            applicationIncomesTable.setSelectionMode(DataGrid.SelectionMode.MULTI_CHECK);
        }
        if (this.treedatagrid.getSelectionMode() == DataGrid.SelectionMode.MULTI_CHECK) {
            this.treedatagrid.setSelectionMode(DataGrid.SelectionMode.SINGLE);
        } else {
            this.treedatagrid.setSelectionMode(DataGrid.SelectionMode.MULTI_CHECK);
        }
    }

    public void onCloseBtnClick() {
        this.close(WINDOW_DISCARD_AND_CLOSE_ACTION);
    }

    public void onExportToPdf() {
        byte[] bytes;
        try {
            bytes = this.appIncomeService.exportReport(this.applicationIncomesDc.getItems(), ReportOutputType.pdf);
            this.exportDisplay.show(new ByteArrayDataProvider(bytes), "appincomes.pdf", ExportFormat.PDF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Пункт во всплывающем окне "Настроить колонки таблицы"
     */
    @Subscribe("settingsBtn.columnsAction")
    private void onSettingsBtnColumnsAction(Action.ActionPerformedEvent event) {
        List<DataGrid.Column<ApplicationIncome>> visibleColumns = this.applicationIncomesTable.getVisibleColumns();
        List<DataGrid.Column<ApplicationIncome>> columns = this.applicationIncomesTable.getColumns();
        List<Integer> list = new ArrayList<>();
        // Получаем массив номеров видимых колонок (от 0 до 6)
        for (Integer i = 0; i < columns.size(); i++) {
            if (visibleColumns.contains(columns.get(i))) {
                list.add(i);
            }
        }
        this.columnsList.setValue(list);

        // Открыть костыльное всплывающее окно со списком колонок
        this.columnsPopup.setPopupVisible(true);
    }

    /**
     * Пункт во всплывающем окне "Настроить фильтр"
     */
    @Subscribe("settingsBtn.filterAction")
    private void onSettingsBtnFilterAction(Action.ActionPerformedEvent event) {
        this.filter.setVisible(true);
    }

    @Subscribe("columnsList")
    private void onColumnsListValueChange(HasValue.ValueChangeEvent event) {
        // После изменения опций в меню скрывает\отображаем нужные колонки таблицы
        Collection<Integer> selected = this.columnsList.getLookupSelectedItems();
        List<DataGrid.Column<ApplicationIncome>> columns = this.applicationIncomesTable.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).setCollapsed(!selected.contains(i));
        }

        List<DataGrid.Column<HierarchyApplicationIncome>> columns2 = this.treedatagrid.getColumns();
        for (int i = 0; i < columns2.size(); i++) {
            columns2.get(i).setCollapsed(!selected.contains(i));
        }
    }

    @Subscribe("applicationIncomesTable")
    private void onRecordSelect(TreeDataGrid.SelectionEvent<ApplicationIncome> event) {
        Set<ApplicationIncome> selected = event.getSelected();
        boolean isShowProcActions = selected.size() == 1;

        if (isShowProcActions) {
            selected.forEach(selectedEntity -> procActionsFragment.initializer()
                    .standard()
                    .init(PROCESS_CODE, selectedEntity));
        }
        procActionsFragment.getFragment().setVisible(isShowProcActions);
    }

    @Subscribe("applicationIncomesTable.print")
    private void onApplicationIncomesTablePrint(Action.ActionPerformedEvent event) {
        byte[] bytes;
        try {
            bytes = this.appIncomeService.printReport(this.applicationIncomesDc.getItems());
            this.exportDisplay.show(new ByteArrayDataProvider(bytes), "list.html", ExportFormat.HTML);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe("applicationIncomesTable.search")
    private void onApplicationIncomesTableSearch(Action.ActionPerformedEvent event) {
        String search = this.searchField.getValue();
        if (search != "" && search != null) {
            search = "(?i)%" + search + "%";
        } else {
            search = null;
        }
        ResultsByFieldValueUpdateUtils.setFilterField(this.applicationIncomesDl, "s1", search);
        ResultsByFieldValueUpdateUtils.setFilterField(this.applicationIncomesDl, "s2", search);
        ResultsByFieldValueUpdateUtils.setFilterField(this.applicationIncomesDl, "s3", search);
        ResultsByFieldValueUpdateUtils.setFilterField(this.applicationIncomesDl, "s4", search);
        this.reloadApplicationsTree();
    }

    private void reloadApplicationsTree() {
        this.applicationIncomesDl.load();
        List<ApplicationIncome> applist = this.applicationIncomesDc.getItems();
        List<HierarchyApplicationIncome> hlist = new ArrayList<>();
        Long numId = 1L;
        for (ApplicationIncome a : applist) {
            HierarchyApplicationIncome h = this.metadata.create(HierarchyApplicationIncome.class);
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

            this.stagesDl.setQuery("select e from spmu_ApplicationStageIncome e where e.application=:a");
            this.stagesDl.setParameter("a", a);
            this.stagesDl.load();

            List<ApplicationStageIncome> stages = this.stagesDc.getItems();
            for (ApplicationStageIncome ar : stages) {
                HierarchyApplicationIncome h2 = this.metadata.create(HierarchyApplicationIncome.class);
                h2.setApp(null);
                h2.setStage(ar);
                h2.setId(numId);
                h2.setParent(h);
                h2.setName(ar.getName());
                h2.setAffiliate(ar.getAffiliate() != null ? ar.getAffiliate().getName() : "");
                h2.setPlanArticle(ar.getPlanArticle() != null ? ar.getPlanArticle().getName() : "");
                h2.setDateStart(ar.getStart());
                h2.setDateEnd(ar.getEnd());
                h2.setValue(ar.getApplicationStageValue() != null ? ar.getApplicationStageValue().getValue() : 0.0);
                numId++;
                hlist.add(h2);
            }
        }

        this.hierarchyApplicationsDc.setItems(hlist);
    }

    @Subscribe("treedatagrid.edit")
    private void onTreeTableEdit(Action.ActionPerformedEvent event) {
        HierarchyApplicationIncome sel = this.treedatagrid.getSingleSelected();
        if (sel == null) return;
        if (sel.getApp() != null) {
            ApplicationIncome edited = this.dataManager.load(ApplicationIncome.class)
                    .id(sel.getApp().getId()).view("applicationIncome-view-create").one();
            this.screenBuilders.editor(ApplicationIncome.class, this)
                .editEntity(edited)
                .withLaunchMode(OpenMode.NEW_WINDOW)
                .withScreenClass(ApplicationIncomeEdit.class)
                .withAfterCloseListener(applicationIncomeEditAfterScreenCloseEvent -> {
                    this.reloadApplicationsTree();
                })
                .build()
                .show();
        } else if (sel.getStage() != null) {
            ApplicationStageIncome edited = this.dataManager.load(ApplicationStageIncome.class)
                    .id(sel.getStage().getId()).view("applicationStageIncome-edit").one();
            screenBuilders.editor(ApplicationStageIncome.class, this)
                    .editEntity(edited)
                    .withLaunchMode(OpenMode.NEW_WINDOW)
                    .withScreenClass(ApplicationStageIncomeEdit.class)
                    .withAfterCloseListener(applicationStageIncomeEditAfterScreenCloseEvent -> {
                        this.reloadApplicationsTree();
                    })
                    .build()
                    .show();
        }
    }

    @Subscribe("create")
    private void onCreate(Action.ActionPerformedEvent event) {
        this.screenBuilders.editor(ApplicationIncome.class, this)
                .newEntity()
                .withLaunchMode(OpenMode.NEW_WINDOW)
                .withScreenClass(ApplicationIncomeEdit.class)
                .withAfterCloseListener(applicationIncomeEditAfterScreenCloseEvent -> {
                    this.reloadApplicationsTree();
                })
                .build()
                .show();
    }

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        this.viewTypeField.setValue(ViewType.HIERARCHY);
    }

    @Subscribe("viewTypeField")
    private void onViewTypeFieldValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue().equals(ViewType.HIERARCHY)) {
            this.treedatagrid.setVisible(true);
            this.applicationIncomesTable.setVisible(false);
        } else {
            this.treedatagrid.setVisible(false);
            this.applicationIncomesTable.setVisible(true);
        }
    }

}
