package com.company.spmu.web.applicationstageexpense;

import com.company.spmu.entity.*;
import com.company.spmu.enumeration.PlanArticleType;
import com.company.spmu.service.AppExpenseService;
import com.company.spmu.service.AppStageService;
import com.company.spmu.web.applicationstageexpense.stages.*;
import com.company.spmu.web.custom.utils.ResultsByFieldValueUpdateUtils;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.export.ByteArrayDataProvider;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstanceLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.yarg.structure.ReportOutputType;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@UiController("spmu_ApplicationStageBrowseExpense")
@UiDescriptor("application-stage-expense-browse-fragment.xml")
public class ApplicationStageExpenseBrowseFragment extends ScreenFragment {
    private ApplicationExpense parentApp;

    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private AppExpenseService appExpenseService;
    @Inject
    private CollectionLoader<ApplicationStageExpense> applicationStageExpensesDl;
    @Inject
    private AppStageService appStageService;
    @Inject
    private InstanceLoader<ApplicationExpense> applicationExpenseDl;
    @Inject
    private CollectionContainer<ApplicationStageExpense> applicationStageExpensesDc;
    @Inject
    private ExportDisplay exportDisplay;
    @Inject
    private TextField<String> searchField;
    @Inject
    private Button createBtn;
    @Inject
    private DataManager dataManager;
    @Inject
    private Notifications notifications;
    @Inject
    private DataGrid<ApplicationStageExpense> applicationStageExpensesTable;

    private ApplicationStageExpenseEditScreenFactory applicationStageExpenseEditScreenFactory;
    @Inject
    private Button copyBtn;
    @Inject
    private Button removeBtn;
    @Inject
    private Button upBtn;
    @Inject
    private Button downBtn;

    @Subscribe
    private void onInit(InitEvent event) {
        applicationStageExpenseEditScreenFactory = new ApplicationStageExpenseEditScreenFactory(screenBuilders)
                .setFrameOwner(this)
                .setAfterScreenCloseEvent(applicationStageExpenseEditAfterScreenCloseEvent -> {
                    this.applicationStageExpensesDl.load();
                    this.applicationExpenseDl.load();
                });
    }

    /**
     * Кнопка "Копировать"
     * @param {Action.ActionPerformedEvent} event
     */
    @Subscribe("applicationStageExpensesTable.copy")
    private void onApplicationStagesTableCopy(Action.ActionPerformedEvent event) {
        Set<ApplicationStageExpense> list = this.applicationStageExpensesTable.getSelected();
        for (ApplicationStage a : list) {
            this.appStageService.copy(a);
        }
        this.applicationStageExpensesDl.load();
    }

    /**
     * Кнопка "Переместить вверх"
     * @param {Action.ActionPerformedEvent} event
     */
    @Subscribe("applicationStageExpensesTable.moveup")
    private void onApplicationStageExpensesTableMoveup(Action.ActionPerformedEvent event) {
        Set<ApplicationStageExpense> list = this.applicationStageExpensesTable.getSelected();
        for (ApplicationStageExpense a : list) {
            this.appStageService.moveUp(a, this.parentApp);
        }
        this.applicationStageExpensesDl.load();
    }

    /**
     * Кнопка "Переместить вниз"
     * @param {Action.ActionPerformedEvent} event
     */
    @Subscribe("applicationStageExpensesTable.movedown")
    private void onApplicationStageExpensesTableMovedown(Action.ActionPerformedEvent event) {
        Set<ApplicationStageExpense> list = this.applicationStageExpensesTable.getSelected();
        for (ApplicationStageExpense a : list) {
            this.appStageService.moveDown(a, this.parentApp);
        }
        this.applicationStageExpensesDl.load();
    }

    @Subscribe("applicationStageExpensesTable.create")
    private void onApplicationStageExpensesTableCreate(Action.ActionPerformedEvent event) {
        ApplicationStageExpense applicationStageExpense = new ApplicationStageExpense();
        applicationStageExpense.setApplication(this.parentApp);
        Screen screen = this.applicationStageExpenseEditScreenFactory
                .buildScreen(applicationStageExpense);
        screen.addAfterCloseListener(afterCloseEvent -> {

            this.appStageService.recalcAppStageOrder(this.parentApp);
            // Обновляем список статей
            this.applicationStageExpensesDl.load();

            // Обновляем заявку
            this.applicationExpenseDl.load();
        });
        screen.show();
    }

    /**
     * Загрузить статьи во вкладке
     * @param parent
     */
    public void setParentApp(ApplicationExpense parent) {
        this.parentApp = parent;

        // Сортируем статьи по order
        this.appStageService.recalcAppStageOrder(this.parentApp);

        // Загружаем статьи
        this.applicationStageExpensesDl.setParameter("parentApp", this.parentApp.getId());
        this.applicationStageExpensesDl.load();

        if (parent != null) {
            this.personalStageColumns(parent);
            this.setupButtons(parent);
        }

        try {
            this.applicationStageExpensesTable.addGeneratedColumn("prevYear", setYears(0));
            this.applicationStageExpensesTable.addGeneratedColumn("year1", setYears(1));
            this.applicationStageExpensesTable.getColumn("year1").setCaption(String.valueOf(parent.getYear() + 1));
            this.applicationStageExpensesTable.addGeneratedColumn("year2", setYears(2));
            this.applicationStageExpensesTable.getColumn("year2").setCaption(String.valueOf(parent.getYear() + 2));
            this.applicationStageExpensesTable.addGeneratedColumn("year3", setYears(3));
            this.applicationStageExpensesTable.getColumn("year3").setCaption(String.valueOf(parent.getYear() + 3));
            this.applicationStageExpensesTable.addGeneratedColumn("year4", setYears(4));
            this.applicationStageExpensesTable.getColumn("year4").setCaption(String.valueOf(parent.getYear() + 4));
        }catch (NullPointerException e){
            System.err.println("Пока не создан родитель в ApplicationExpense " + e);
        }
        if(PersistenceHelper.isNew(parentApp)) {
            this.createBtn.setEnabled(false);
        }
        else {
            this.createBtn.setEnabled(true);
        }

    }

    private DataGrid.ColumnGenerator<ApplicationStageExpense, String> setYears(Integer number) {
        return new DataGrid.ColumnGenerator<ApplicationStageExpense, String>() {
            @Override
            public String getValue(DataGrid.ColumnGeneratorEvent<ApplicationStageExpense> event) {
                List<ApplicationStageValue> applicationStageValues = appExpenseService.getParentList(event.getItem().getApplicationStageValue());
                try {
                    return String.valueOf(applicationStageValues.get(number).getValue());
                }
                catch (ArrayIndexOutOfBoundsException e) {
                }
                return "";
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }
        };
    }

    @Subscribe("applicationStageExpensesTable.edit")
    private void onApplicationStageExpensesTableEdit(Action.ActionPerformedEvent event) {
        ApplicationStageExpense edit = this.applicationStageExpensesTable.getSingleSelected();
        if (this.isStageCreatedFromPlanCanEdited(edit)) {

            applicationStageExpenseEditScreenFactory
                    .buildScreen(edit, ApplicationStageExpenseEditScreenFactory.Mode.EDIT)
                    .show();
        }
    }

    @Subscribe("applicationStageExpensesTable.print")
    private void onApplicationStageExpensesTablePrint(Action.ActionPerformedEvent event) {
        byte[] bytes;
        try {
            bytes = this.appStageService.printReportExpense(this.applicationStageExpensesDc.getItems());
            this.exportDisplay.show(new ByteArrayDataProvider(bytes), "list.html", ExportFormat.HTML);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe("applicationStageExpensesTable.search")
    private void onApplicationStageExpensesTableSearch(Action.ActionPerformedEvent event) {
        String search = this.searchField.getValue();
        if (search != null && !search.equals("")) {
            search = "%" + search + "%";
        } else {
            search = null;
        }
        ResultsByFieldValueUpdateUtils.updateResultsByFilterField(this.applicationStageExpensesDl, "s1", search);
        ResultsByFieldValueUpdateUtils.updateResultsByFilterField(this.applicationStageExpensesDl, "s2", search);
        ResultsByFieldValueUpdateUtils.updateResultsByFilterField(this.applicationStageExpensesDl, "s3", search);
        ResultsByFieldValueUpdateUtils.updateResultsByFilterField(this.applicationStageExpensesDl, "s4", search);
        ResultsByFieldValueUpdateUtils.updateResultsByFilterField(this.applicationStageExpensesDl, "s5", search);
        try {
            ResultsByFieldValueUpdateUtils.updateResultsByFilterField(this.applicationStageExpensesDl, "s6", Integer.parseInt(search));
        }
        catch (NumberFormatException e) {
            ResultsByFieldValueUpdateUtils.updateResultsByFilterField(this.applicationStageExpensesDl, "s6", null);
        }
    }

    private boolean isStageCreatedFromPlanCanEdited(ApplicationStageExpense edit) {
        return this.appExpenseService.isStageCreatedFromPlanCanEdited(edit);
    }

    private void personalStageColumns(ApplicationExpense app) {
        this.setColumnVisible("planArticle", false);
        this.setColumnVisible("name", false);
        this.setColumnVisible("q1", false);
        this.setColumnVisible("q2", false);
        this.setColumnVisible("q3", false);
        this.setColumnVisible("q4", false);
        this.setColumnVisible("a1", false);
        this.setColumnVisible("affiliate", false);
        this.setColumnVisible("contract", false);
        this.setColumnVisible("a2", false);
        this.setColumnVisible("a3", false);
        this.setColumnVisible("a4", false);
        this.setColumnVisible("description", false);
        this.setColumnVisible("value", false);
        this.setColumnVisible("contractend", false);
        this.setColumnVisible("name2", false);
        this.setColumnVisible("a5", false);
        this.setColumnVisible("name3", false);
        this.setColumnVisible("contractcode", false);
        this.setColumnVisible("a6", false);
        this.setColumnVisible("a7", false);
        this.setColumnVisible("a8", false);
        this.setColumnVisible("a9", false);
        this.setColumnVisible("a10", false);
        this.setColumnVisible("a11", false);
        this.setColumnVisible("a12", false);
        this.setColumnVisible("a13", false);
        this.setColumnVisible("a14", false);
        this.setColumnVisible("a15", false);
        this.setColumnVisible("a16", false);
        this.setColumnVisible("a17", false);
        this.setColumnVisible("a18", false);
        this.setColumnVisible("a19", false);
        this.setColumnVisible("a20", false);
        this.setColumnVisible("a21", false);
        this.setColumnVisible("a22", false);
        this.setColumnVisible("a23", false);
        this.setColumnVisible("a24", false);
        this.setColumnVisible("a25", false);
        this.setColumnVisible("a26", false);
        this.setColumnVisible("a27", false);

        PlanArticleType code;
        try {
            code = PlanArticleType.fromId(app.getPlanArticle().getCode());
        }
        catch (NullPointerException e) {
            return;
        }
        switch (code) {
            case CODE_20120: {
                this.setColumnCaption("name", "Статья");
                this.setColumnVisible("name", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20200: {
                this.setColumnCaption("name", "Показатели");
                this.setColumnVisible("name", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20301: {
                this.setColumnVisible("a1", true);
                this.setColumnVisible("affiliate", true);
                this.setColumnVisible("contract", true);
                this.setColumnVisible("a2", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                this.setColumnVisible("a4", true);
                break;
            }
            case CODE_20302: {
                this.setColumnCaption("name", "Наименование аэродрома");
                this.setColumnVisible("name", true);
                this.setColumnVisible("contract", true);
                this.setColumnVisible("a3", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20303: {
                this.setColumnVisible("affiliate", true);
                this.setColumnVisible("name2", true);
                this.setColumnVisible("a5", true);
                this.setColumnVisible("contractend", true);
                this.setColumnVisible("a3", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20304: {
                this.setColumnVisible("name2", true);
                this.setColumnVisible("name3", true);
                this.setColumnVisible("contractcode", true);
                this.setColumnVisible("contractend", true);
                this.setColumnVisible("a3", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20305: {
                this.setColumnVisible("affiliate", true);
                this.setColumnVisible("a6", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20306: {
                this.setColumnVisible("a7", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20307: {
                this.setColumnVisible("affiliate", true);
                this.setColumnVisible("a6", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20308: {
                this.setColumnCaption("name", "Наименование аэродрома");
                this.setColumnVisible("name", true);
                this.setColumnVisible("contractcode", true);
                this.setColumnVisible("contractend", true);
                this.setColumnVisible("a3", true);
                this.setColumnVisible("a8", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20309: {
                this.setColumnVisible("a9", true);
                this.setColumnVisible("a10", true);
                this.setColumnVisible("a11", true);
                this.setColumnVisible("a12", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20310: {
                this.setColumnVisible("a13", true);
                this.setColumnVisible("a14", true);
                this.setColumnVisible("a15", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20401: {
                this.setColumnVisible("a16", true);
                this.setColumnVisible("a17", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20402: {
                this.setColumnVisible("name", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20403: {
                this.setColumnVisible("a18", true);
                this.setColumnVisible("contract", true);
                this.setColumnVisible("a19", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20404: {
                this.setColumnVisible("a20", true);
                this.setColumnVisible("contractcode", true);
                this.setColumnVisible("contractend", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20405: {
                this.setColumnVisible("a16", true);
                this.setColumnVisible("a21", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20406: {
                this.setColumnVisible("a22", true);
                this.setColumnVisible("a23", true);
                this.setColumnVisible("a24", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20407: {
                this.setColumnCaption("name", "Наименование мероприятия");
                this.setColumnVisible("name", true);
                this.setColumnVisible("a25", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20408: {
                this.setColumnCaption("name", "Наименование расходов, объекта");
                this.setColumnVisible("name", true);
                this.setColumnVisible("a26", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            case CODE_20500: {
                this.setColumnVisible("a27", true);
                this.setColumnVisible("contractcode", true);
                this.setColumnVisible("contractend", true);
                this.setColumnVisible("q1", true);
                this.setColumnVisible("q2", true);
                this.setColumnVisible("q3", true);
                this.setColumnVisible("q4", true);
                break;
            }
            default: {
                break;
            }
        }
    }

    private void setColumnVisible(String name, Boolean visible) {
        this.applicationStageExpensesTable.getColumn(name).setVisible(visible);
    }

    private void setColumnCaption(String name, String caption) {
        DataGrid.Column<ApplicationStageExpense> column = this.applicationStageExpensesTable.getColumn(name);
        column.setCaption(caption);
    }

    @Subscribe("exportButton.exportToPdf")
    private void onExportButtonExportToPdf(Action.ActionPerformedEvent event) {
        byte[] bytes;
        try {
            if(this.applicationStageExpensesDc.getItems().size() == 0){
                this.notifications.create().withCaption("Нет элементов для экспорта").show();
                return;
            }
            bytes = this.appStageService.exportReportStageExpense(this.applicationStageExpensesDc.getItems(), ReportOutputType.pdf, PlanArticleType.fromId(parentApp.getPlanArticle().getCode()));
            this.exportDisplay.show(new ByteArrayDataProvider(bytes), "appstageexpenses.pdf", ExportFormat.PDF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setupButtons(ApplicationExpense app) {
        try {
            PlanArticleType code = PlanArticleType.fromId(app.getPlanArticle().getCode());
            if (code == PlanArticleType.CODE_20120 || code == PlanArticleType.CODE_20200 || code == PlanArticleType.CODE_20402 || code == PlanArticleType.CODE_20500) {
                this.createBtn.setVisible(false);
                this.copyBtn.setVisible(false);
                this.removeBtn.setVisible(false);
                this.upBtn.setVisible(false);
                this.downBtn.setVisible(false);
            }
        }
        catch (NullPointerException e) {}
    }

    @Subscribe("applicationStageExpensesTable.remove")
    private void onApplicationStageExpensesTableRemove(Action.ActionPerformedEvent event) {
        this.dataManager.remove(this.applicationStageExpensesTable.getSingleSelected());
        this.appStageService.recalcAppStageOrder(this.parentApp);
        this.applicationStageExpensesDl.load();
    }

}
