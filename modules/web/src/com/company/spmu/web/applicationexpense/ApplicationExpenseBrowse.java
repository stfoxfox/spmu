package com.company.spmu.web.applicationexpense;

import com.company.spmu.entity.*;
import com.company.spmu.enumeration.ApplicationType;
import com.company.spmu.enumeration.ViewType;
import com.company.spmu.service.AppExpenseService;
import com.company.spmu.web.applicationstageexpense.ApplicationStageExpenseEdit;
import com.company.spmu.web.applicationstageexpense.stages.ApplicationStageExpenseEditScreenFactory;
import com.company.spmu.web.bmp.fragment.procactionsfragment.ProcActionsFragment;
import com.company.spmu.web.custom.utils.ResultsByFieldValueUpdateUtils;
import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.UserSessionSource;
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
import com.haulmont.reports.entity.Report;
import com.haulmont.reports.gui.ReportGuiManager;
import com.haulmont.yarg.structure.ReportOutputType;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

@UiController("spmu_ApplicationExpense.browse")
@UiDescriptor("application-expense-browse.xml")
@LookupComponent("applicationExpensesTable")
@LoadDataBeforeShow
public class ApplicationExpenseBrowse extends StandardLookup<ApplicationExpense> {
    protected static final String PROCESS_CODE = "applicationExpenseprocessmodel";
    private ApplicationStageExpenseEditScreenFactory applicationStageExpenseEditScreenFactory;

    @Inject
    private Filter filter;
    @Inject
    private CollectionLoader<ApplicationExpense> applicationExpensesDl;
    @Inject
    private TreeDataGrid<ApplicationExpense> applicationExpensesTable;
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
    private DataService dataService;
    @Inject
    private ReportGuiManager reportGuiManager;
    @Inject
    private CollectionContainer<ApplicationExpense> applicationExpensesDc;
    @Inject
    private AppExpenseService appExpenseService;
    @Inject
    private ExportDisplay exportDisplay;
    @Inject
    private TextField<String> searchField;
    @Inject
    private CollectionLoader<Affilate> affiliateDl;
    @Inject
    private LookupField<Affilate> affiliateLookupField;
    @Inject
    private TreeDataGrid<HierarchyApplicationExpense> treedatagrid;
    @Inject
    private LookupField viewTypeField;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionLoader<ApplicationStageExpense> stagesDl;
    @Inject
    private CollectionContainer<ApplicationStageExpense> stagesDc;
    @Inject
    private CollectionContainer<HierarchyApplicationExpense> hierarchyApplicationsDc;
    @Inject
    private DataManager dataManager;

    @Subscribe("affiliateLookupField")
    private void onAffiliateLookupFieldValueChange(HasValue.ValueChangeEvent<Affilate> event) {
        ResultsByFieldValueUpdateUtils.setFilterField(applicationExpensesDl, "affiliate", event.getValue());
        ResultsByFieldValueUpdateUtils.updateResultsByFilterField(divisionsDl, "affiliate", event.getValue());
        this.reloadApplicationsTree();
    }

    @Subscribe("divisionLookupField")
    private void onDivisionLookupFieldValueChange(HasValue.ValueChangeEvent<Division> event) {
        ResultsByFieldValueUpdateUtils.setFilterField(applicationExpensesDl, "division", event.getValue());
        this.reloadApplicationsTree();
    }

    @Subscribe("articleLookupField")
    private void onArticleLookupFieldValueChange(HasValue.ValueChangeEvent<PlanArticle> event) {
        ResultsByFieldValueUpdateUtils.setFilterField(applicationExpensesDl, "planArticle", event.getValue());
        this.reloadApplicationsTree();
    }

    @Subscribe("initiatorLookupField")
    private void onInitiatorLookupFieldValueChange(HasValue.ValueChangeEvent<Employee> event) {
        ResultsByFieldValueUpdateUtils.setFilterField(applicationExpensesDl, "applicationResponsibleInitiator", event.getValue());
        this.reloadApplicationsTree();
    }

    @Subscribe("correctionLookup")
    private void onCorrectionLookupValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        ResultsByFieldValueUpdateUtils.setFilterField(applicationExpensesDl, "change", event.getValue());
        this.reloadApplicationsTree();
    }

    @Subscribe("statusTypeField")
    private void onStatusTypeFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        ResultsByFieldValueUpdateUtils.setFilterField(applicationExpensesDl, "status", event.getValue());
        this.reloadApplicationsTree();
    }

    @Subscribe
    private void onInit(InitEvent event) {
        loadApplications();
        initColumnsMenu();
        //setUnvisableColumn();

        applicationExpensesTable.addItemClickListener(applicationExpenseItemClickEvent -> {
            if(applicationExpenseItemClickEvent.getItem() != null) {
                if (applicationExpenseItemClickEvent.getColumnId().equals("name") || applicationExpenseItemClickEvent.getColumnId().equals("code")) {
                    ApplicationExpense applicationExpense = applicationExpenseItemClickEvent.getItem();
                    screenBuilders.editor(ApplicationExpense.class, this)
                            .editEntity(applicationExpense)
                            .withLaunchMode(OpenMode.NEW_WINDOW)
                            .build()
                            .show();
                } else {
                    applicationExpensesTable.setSelected(applicationExpenseItemClickEvent.getItem());
                }
            }
        });

        this.applicationStageExpenseEditScreenFactory = new ApplicationStageExpenseEditScreenFactory(screenBuilders)
            .setFrameOwner(this)
            .setAfterScreenCloseEvent(applicationStageExpenseEditAfterScreenCloseEvent -> this.reloadApplicationsTree());
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
            ResultsByFieldValueUpdateUtils.setFilterField(applicationExpensesDl, "affiliate", user.getAffiliate());
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
        //applicationExpensesTable.getColumn("status").setVisible(false);
        //applicationExpensesTable.getColumn("plan").setVisible(false);
        //applicationExpensesTable.getColumn("type").setVisible(false);
        //applicationExpensesTable.getColumn("change").setVisible(false);
        //applicationExpensesTable.getColumn("kind").setVisible(false);
        //applicationExpensesTable.getColumn("active").setVisible(false);
        //applicationExpensesTable.getColumn("date").setVisible(false);
        //applicationExpensesTable.getColumn("description").setVisible(false);
        //applicationExpensesTable.getColumn("order").setVisible(false);
        //applicationExpensesTable.getColumn("layout").setVisible(false);
        //applicationExpensesTable.getColumn("assignment").setVisible(false);
        //applicationExpensesTable.getColumn("proof").setVisible(false);
        //applicationExpensesTable.getColumn("year").setVisible(false);
    }

    @Subscribe("applicationExpensesTable.edit")
    private void onApplicationExpensesTableEdit(Action.ActionPerformedEvent event) {
        // При клике в таблице открываем сначала статьи а потом только редактор сущности
        screenBuilders.editor(ApplicationExpense.class, this)
                .editEntity(this.applicationExpensesTable.getSingleSelected())
                .withLaunchMode(OpenMode.NEW_WINDOW)
                .build()
                .show();
    }

    public void onFilterBtnClick() {
        filter.setVisible(!filter.isVisible());
    }

    public void onSelectBtnClick() {
        if (applicationExpensesTable.getSelectionMode() == DataGrid.SelectionMode.MULTI_CHECK) {
            applicationExpensesTable.setSelectionMode(DataGrid.SelectionMode.SINGLE);
        } else {
            applicationExpensesTable.setSelectionMode(DataGrid.SelectionMode.MULTI_CHECK);
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
            bytes = this.appExpenseService.exportReport(this.applicationExpensesDc.getItems(), ReportOutputType.pdf);
            this.exportDisplay.show(new ByteArrayDataProvider(bytes), "appexpenses.pdf", ExportFormat.PDF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Пункт во всплывающем окне "Настроить колонки таблицы"
     */
    @Subscribe("settingsBtn.columnsAction")
    private void onSettingsBtnColumnsAction(Action.ActionPerformedEvent event) {
        List<DataGrid.Column<ApplicationExpense>> visibleColumns = this.applicationExpensesTable.getVisibleColumns();
        List<DataGrid.Column<ApplicationExpense>> columns = this.applicationExpensesTable.getColumns();
        List<Integer> list = new ArrayList<>();
        // Получаем массив номеров видимых колонок (от 0 до 6)
        for (Integer i = 0; i < columns.size(); i++) {
            if (!columns.get(i).isCollapsed()) {
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
        List<DataGrid.Column<ApplicationExpense>> columns = this.applicationExpensesTable.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).setCollapsed(!selected.contains(i));
        }

        List<DataGrid.Column<HierarchyApplicationExpense>> columns2 = this.treedatagrid.getColumns();
        for (int i = 0; i < columns2.size(); i++) {
            columns2.get(i).setCollapsed(!selected.contains(i));
        }
    }

    @Subscribe("applicationExpensesTable")
    private void onRecordSelect(TreeDataGrid.SelectionEvent<ApplicationExpense> event) {
        Set<ApplicationExpense> selected = event.getSelected();
        boolean isShowProcActions = selected.size() == 1;

        if (isShowProcActions) {
            selected.forEach(selectedEntity -> procActionsFragment.initializer()
                    .standard()
                    .init(PROCESS_CODE, selectedEntity));
        }
        procActionsFragment.getFragment().setVisible(isShowProcActions);
    }

    @Subscribe("applicationExpensesTable.print")
    private void onApplicationExpensesTablePrint(Action.ActionPerformedEvent event) {
        byte[] bytes;
        try {
            bytes = this.appExpenseService.printReport(this.applicationExpensesDc.getItems());
            this.exportDisplay.show(new ByteArrayDataProvider(bytes), "list.html", ExportFormat.HTML);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe("applicationExpensesTable.search")
    private void onApplicationExpensesTableSearch(Action.ActionPerformedEvent event) {
        String search = this.searchField.getValue();
        if (search != "" && search != null) {
            search = "%" + search + "%";
        } else {
            search = null;
        }
        ResultsByFieldValueUpdateUtils.setFilterField(this.applicationExpensesDl, "s1", search);
        ResultsByFieldValueUpdateUtils.setFilterField(this.applicationExpensesDl, "s2", search);
        ResultsByFieldValueUpdateUtils.setFilterField(this.applicationExpensesDl, "s3", search);
        ResultsByFieldValueUpdateUtils.setFilterField(this.applicationExpensesDl, "s4", search);
        this.reloadApplicationsTree();
    }

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        this.viewTypeField.setValue(ViewType.HIERARCHY);
    }

    @Subscribe("viewTypeField")
    private void onViewTypeFieldValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue().equals(ViewType.HIERARCHY)) {
            this.treedatagrid.setVisible(true);
            this.applicationExpensesTable.setVisible(false);
        } else {
            this.treedatagrid.setVisible(false);
            this.applicationExpensesTable.setVisible(true);
        }
    }

    private void reloadApplicationsTree() {
        this.applicationExpensesDl.load();
        List<ApplicationExpense> applist = this.applicationExpensesDc.getItems();
        List<HierarchyApplicationExpense> hlist = new ArrayList<>();
        Long numId = 1L;
        for (ApplicationExpense a : applist) {
            HierarchyApplicationExpense h = this.metadata.create(HierarchyApplicationExpense.class);
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

            this.stagesDl.setQuery("select e from spmu_ApplicationStageExpense e where e.application=:a");
            this.stagesDl.setParameter("a", a);
            this.stagesDl.load();

            List<ApplicationStageExpense> stages = this.stagesDc.getItems();
            for (ApplicationStageExpense ar : stages) {
                HierarchyApplicationExpense h2 = this.metadata.create(HierarchyApplicationExpense.class);
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
    private void onTreedatagridEdit(Action.ActionPerformedEvent event) {
        HierarchyApplicationExpense sel = this.treedatagrid.getSingleSelected();
        if (sel == null) return;
        if (sel.getApp() != null) {
            ApplicationExpense edited = this.dataManager.load(ApplicationExpense.class)
                    .id(sel.getApp().getId()).view("applicationExpense-view-create").one();
            this.screenBuilders.editor(ApplicationExpense.class, this)
                    .editEntity(edited)
                    .withLaunchMode(OpenMode.NEW_WINDOW)
                    .withScreenClass(ApplicationExpenseEdit.class)
                    .withAfterCloseListener(applicationExpenseEditAfterScreenCloseEvent -> {
                        this.reloadApplicationsTree();
                    })
                    .build()
                    .show();
        } else if (sel.getStage() != null) {
            ApplicationStageExpense edited = this.dataManager.load(ApplicationStageExpense.class)
                    .id(sel.getStage().getId()).view("applicationStageExpense-edit").one();
            applicationStageExpenseEditScreenFactory
                    .buildScreen(edited, ApplicationStageExpenseEditScreenFactory.Mode.EDIT)
                    .show();
        }
    }

}
