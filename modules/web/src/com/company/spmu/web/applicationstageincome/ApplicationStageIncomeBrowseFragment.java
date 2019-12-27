package com.company.spmu.web.applicationstageincome;

import com.company.spmu.entity.*;
import com.company.spmu.service.AppIncomeService;
import com.company.spmu.service.AppStageService;
import com.company.spmu.web.custom.utils.ResultsByFieldValueUpdateUtils;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
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
import java.util.*;

@UiController("spmu_ApplicationStageBrowseIncome")
@UiDescriptor("application-stage-income-browse-fragment.xml")
public class ApplicationStageIncomeBrowseFragment extends ScreenFragment {
    private ApplicationIncome parentApp;

    @Inject
    private GroupTable<ApplicationStageIncome> applicationStageIncomesTable;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private UiComponents uiComponents;
    @Inject
    private AppIncomeService appIncomeService;
    @Inject
    private CollectionLoader<ApplicationStageIncome> applicationStageIncomesDl;
    @Inject
    private AppStageService appStageService;
    @Inject
    private InstanceLoader<ApplicationIncome> applicationIncomeDl;
    @Inject
    private CollectionContainer<ApplicationStageIncome> applicationStageIncomesDc;
    @Inject
    private ExportDisplay exportDisplay;
    @Inject
    private TextField<String> searchField;
    @Inject
    private DataManager dataManager;
    @Inject
    private Button createBtn;

    /**
     * Кнопка "Копировать"
     * @param {Action.ActionPerformedEvent} event
     */
    @Subscribe("applicationStageIncomesTable.copy")
    private void onApplicationStagesTableCopy(Action.ActionPerformedEvent event) {
        Set<ApplicationStageIncome> list = this.applicationStageIncomesTable.getSelected();
        for (ApplicationStage a : list) {
            this.appStageService.copy(a);
        }
        this.applicationStageIncomesDl.load();
    }

    /**
     * Кнопка "Переместить вверх"
     * @param {Action.ActionPerformedEvent} event
     */
    @Subscribe("applicationStageIncomesTable.moveup")
    private void onApplicationStageIncomesTableMoveup(Action.ActionPerformedEvent event) {
        Set<ApplicationStageIncome> list = this.applicationStageIncomesTable.getSelected();
        for (ApplicationStageIncome a : list) {
            this.appStageService.moveUp(a, this.parentApp);
        }
        this.applicationStageIncomesDl.load();
    }

    /**
     * Кнопка "Переместить вниз"
     * @param {Action.ActionPerformedEvent} event
     */
    @Subscribe("applicationStageIncomesTable.movedown")
    private void onApplicationStageIncomesTableMovedown(Action.ActionPerformedEvent event) {
        Set<ApplicationStageIncome> list = this.applicationStageIncomesTable.getSelected();
        for (ApplicationStageIncome a : list) {
            this.appStageService.moveDown(a, this.parentApp);
        }
        this.applicationStageIncomesDl.load();
    }

    @Subscribe("applicationStageIncomesTable.create")
    private void onApplicationStageIncomesTableCreate(Action.ActionPerformedEvent event) {
        ApplicationStageIncome applicationStageIncome = this.dataManager.create(ApplicationStageIncome.class);
        applicationStageIncome.setApplication(this.parentApp);
        Screen screen = screenBuilders.editor(ApplicationStageIncome.class, this.getFragment().getFrameOwner())
                .newEntity(applicationStageIncome)
                .withLaunchMode(OpenMode.NEW_WINDOW)
                .build();
        screen.addAfterCloseListener(afterCloseEvent -> {
            // Сортируем статьи по order
            this.appStageService.recalcAppStageOrder(this.parentApp);

            // Обновляем список статей
            this.applicationStageIncomesDl.load();

            // Обновляем заявку
            this.applicationIncomeDl.load();
        });
        screen.show();
    }

    /**
     * Загрузить статьи во вкладке
     * @param parent
     */
    public void setParentApp(ApplicationIncome parent) {
        this.parentApp = parent;

        // Сортируем статьи по order
        this.appStageService.recalcAppStageOrder(this.parentApp);

        // Загружаем статьи
        Map<String, Object> values = new HashMap<>();
        values.put("parentApp", this.parentApp.getId());
        values.put("s1", null);
        values.put("s2", null);
        values.put("s3", null);
        values.put("s4", null);
        values.put("s5", null);
        ResultsByFieldValueUpdateUtils.updateResultsByFilterFields(this.applicationStageIncomesDl, values);
        try {
            this.applicationStageIncomesTable.addGeneratedColumn("prevYear", setYears(0));
            this.applicationStageIncomesTable.addGeneratedColumn("year1", setYears(1));
            this.applicationStageIncomesTable.getColumn("year1").setCaption(String.valueOf(parent.getYear() + 1));
            this.applicationStageIncomesTable.addGeneratedColumn("year2", setYears(2));
            this.applicationStageIncomesTable.getColumn("year2").setCaption(String.valueOf(parent.getYear() + 2));
            this.applicationStageIncomesTable.addGeneratedColumn("year3", setYears(3));
            this.applicationStageIncomesTable.getColumn("year3").setCaption(String.valueOf(parent.getYear() + 3));
            this.applicationStageIncomesTable.addGeneratedColumn("year4", setYears(4));
            this.applicationStageIncomesTable.getColumn("year4").setCaption(String.valueOf(parent.getYear() + 4));
        }catch (NullPointerException e){
            System.out.println("Пока не создан родитель в ApplicationIncome " + e);
        }
        if(PersistenceHelper.isNew(parentApp)) {
            this.createBtn.setEnabled(false);
        }
        else {
            this.createBtn.setEnabled(true);
        }
    }

    private Table.PrintableColumnGenerator<ApplicationStageIncome, Component> setYears(Integer number){
        return new Table.PrintableColumnGenerator<ApplicationStageIncome, Component>(){
            @Override
            public Component getValue(ApplicationStageIncome item) {
                Label<Double> label = uiComponents.create(Label.NAME);
                List<ApplicationStageValue> applicationStageValues = appIncomeService.getParentList(item.getApplicationStageValue());
                label.setValue(applicationStageValues.get(number).getValue());
                return label;
            }
            @Override
            public Component generateCell(ApplicationStageIncome item) {
                Label<Double> label = uiComponents.create(Label.NAME);
                List<ApplicationStageValue> applicationStageValues = appIncomeService.getParentList(item.getApplicationStageValue());
                label.setValue(applicationStageValues.get(number).getValue());
                return label;
            }

        };
    }

    @Subscribe("applicationStageIncomesTable.edit")
    private void onApplicationStageIncomesTableEdit(Action.ActionPerformedEvent event) {
        ApplicationStageIncome edited = this.dataManager.load(ApplicationStageIncome.class)
                .id(this.applicationStageIncomesTable.getSingleSelected().getId()).view("applicationStageIncome-edit").one();
        Screen screen = screenBuilders.editor(ApplicationStageIncome.class, this.getFragment().getFrameOwner())
                .editEntity(edited)
                .withLaunchMode(OpenMode.NEW_WINDOW)
                .build();
        screen.addAfterCloseListener(afterCloseEvent -> { // После закрытия редактора
            // Обновляем список статей
            this.applicationStageIncomesDl.load();

            // Обновляем заявку
            this.applicationIncomeDl.load();
        });
        screen.show();
    }

    @Subscribe("applicationStageIncomesTable.print")
    private void onApplicationStageIncomesTablePrint(Action.ActionPerformedEvent event) {
        byte[] bytes;
        try {
            bytes = this.appStageService.printReportIncome(this.applicationStageIncomesDc.getItems());
            this.exportDisplay.show(new ByteArrayDataProvider(bytes), "list.html", ExportFormat.HTML);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe("applicationStageIncomesTable.search")
    private void onApplicationStageIncomesTableSearch(Action.ActionPerformedEvent event) {
        String search = this.searchField.getValue();
        if (search != "" && search != null) {
            search = "(?i)%" + search + "%";
        } else {
            search = null;
        }

        Integer i;
        try {
            i = Integer.parseInt(search);
        }
        catch (NumberFormatException e) {
            i = null;
        }

        Map<String, Object> values = new HashMap<>();
        values.put("parentApp", this.parentApp.getId());
        values.put("s1", search);
        values.put("s2", search);
        values.put("s3", search);
        values.put("s4", search);
        values.put("s5", search);
        values.put("s6", i);
        ResultsByFieldValueUpdateUtils.updateResultsByFilterFields(this.applicationStageIncomesDl, values);
    }

    @Subscribe("exportButton.exportToPdf")
    private void onExportButtonExportToPdf(Action.ActionPerformedEvent event) {
        byte[] bytes;
        try {
            bytes = this.appStageService.exportReportStageIncome(this.applicationStageIncomesDc.getItems(), ReportOutputType.pdf);
            this.exportDisplay.show(new ByteArrayDataProvider(bytes), "appstageincomes.pdf", ExportFormat.PDF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
