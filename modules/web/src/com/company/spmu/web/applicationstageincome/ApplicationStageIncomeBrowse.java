package com.company.spmu.web.applicationstageincome;

import com.company.spmu.entity.ApplicationIncome;
import com.company.spmu.entity.ApplicationStage;
import com.company.spmu.service.AppStageService;
import com.company.spmu.web.OptionScreenStage;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ApplicationStageIncome;
import org.checkerframework.checker.units.qual.A;

import javax.inject.Inject;
import java.util.Set;

@UiController("spmu_ApplicationStageIncome.browse")
@UiDescriptor("application-stage-income-browse.xml")
@LookupComponent("applicationStageIncomesTable")
@LoadDataBeforeShow
public class ApplicationStageIncomeBrowse extends StandardLookup<ApplicationStageIncome> {

    private ApplicationIncome parentApp;

    @Inject
    private GroupTable<ApplicationStageIncome> applicationStageIncomesTable;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private CollectionLoader<ApplicationStageIncome> applicationStageIncomesDl;
    @Inject
    private AppStageService appStageService;

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

    @Subscribe
    private void onInit(InitEvent event) {
        // Определяем заявку к которой будут относится отображаемые статьи
        ScreenOptions options = event.getOptions();
        if (options instanceof OptionScreenStage) {
            this.parentApp = (ApplicationIncome) ((OptionScreenStage) options).getApplication();
        }
        if (this.parentApp != null) {
            this.applicationStageIncomesDl.setParameter("parentApp", this.parentApp.getId());

            // Сортируем статьи по order
            this.appStageService.recalcAppStageOrder(this.parentApp);
        }
        //this.applicationStageIncomesDl.setParameter("dtype", "spmu_ApplicationStageIncome");
    }

    @Subscribe("createBtn")
    private void onCreateBtnClick(Button.ClickEvent event) {
        ApplicationStageIncome applicationStageIncome = new ApplicationStageIncome();
        applicationStageIncome.setApplication(this.parentApp);
        screenBuilders.editor(ApplicationStageIncome.class, this)
                .withScreenClass(ApplicationStageIncomeEdit.class).newEntity(applicationStageIncome)
                .withLaunchMode(OpenMode.NEW_WINDOW)
                .build()
                .show();
    }
}
