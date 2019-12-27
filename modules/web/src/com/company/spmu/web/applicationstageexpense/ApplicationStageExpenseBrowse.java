package com.company.spmu.web.applicationstageexpense;

import com.company.spmu.entity.ApplicationExpense;
import com.company.spmu.entity.ApplicationStage;
import com.company.spmu.service.AppStageService;
import com.company.spmu.web.OptionScreenStage;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ApplicationStageExpense;
import org.checkerframework.checker.units.qual.A;

import javax.inject.Inject;
import java.util.Set;

@UiController("spmu_ApplicationStageExpense.browse")
@UiDescriptor("application-stage-expense-browse.xml")
@LookupComponent("applicationStageExpensesTable")
@LoadDataBeforeShow
public class ApplicationStageExpenseBrowse extends StandardLookup<ApplicationStageExpense> {

    private ApplicationExpense parentApp;

    @Inject
    private GroupTable<ApplicationStageExpense> applicationStageExpensesTable;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private CollectionLoader<ApplicationStageExpense> applicationStageExpensesDl;
    @Inject
    private AppStageService appStageService;

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

    @Subscribe
    private void onInit(InitEvent event) {
        // Определяем заявку к которой будут относится отображаемые статьи
        ScreenOptions options = event.getOptions();
        if (options instanceof OptionScreenStage) {
            this.parentApp = (ApplicationExpense) ((OptionScreenStage) options).getApplication();
        }
        if (this.parentApp != null) {
            this.applicationStageExpensesDl.setParameter("parentApp", this.parentApp.getId());

            // Сортируем статьи по order
            this.appStageService.recalcAppStageOrder(this.parentApp);
        }
        //this.applicationStageExpensesDl.setParameter("dtype", "spmu_ApplicationStageExpense");
    }

    @Subscribe("createBtn")
    private void onCreateBtnClick(Button.ClickEvent event) {
        ApplicationStageExpense applicationStageExpense = new ApplicationStageExpense();
        applicationStageExpense.setApplication(this.parentApp);
        screenBuilders.editor(ApplicationStageExpense.class, this)
                .withScreenClass(ApplicationStageExpenseEdit.class).newEntity(applicationStageExpense)
                .withLaunchMode(OpenMode.NEW_WINDOW)
                .build()
                .show();
    }
}
