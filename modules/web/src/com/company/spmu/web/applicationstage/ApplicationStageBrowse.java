package com.company.spmu.web.applicationstage;

import com.company.spmu.entity.ApplicationIncome;
import com.company.spmu.service.AppStageService;
import com.company.spmu.web.OptionScreenStage;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ApplicationStage;

import javax.inject.Inject;
import java.util.Set;

@UiController("spmu_ApplicationStage.browse")
@UiDescriptor("application-stage-browse.xml")
@LookupComponent("applicationStagesTable")
@LoadDataBeforeShow
public class ApplicationStageBrowse extends StandardLookup<ApplicationStage> {

    private ApplicationIncome parentApp;

    @Inject
    private GroupTable<ApplicationStage> applicationStagesTable;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private CollectionLoader<ApplicationStage> applicationStagesDl;
    @Inject
    private AppStageService appStageService;

    /**
     * Кнопка "Копировать"
     * @param {Action.ActionPerformedEvent} event
     */
    @Subscribe("applicationStagesTable.copy")
    private void onApplicationStagesTableCopy(Action.ActionPerformedEvent event) {
        Set<ApplicationStage> list = this.applicationStagesTable.getSelected();
        for (ApplicationStage a : list) {
            this.appStageService.copy(a);
        }
        this.applicationStagesDl.load();
    }

    /**
     * Кнопка "Переместить вверх"
     * @param {Action.ActionPerformedEvent} event
     */
    @Subscribe("applicationStagesTable.moveup")
    private void onApplicationStagesTableMoveup(Action.ActionPerformedEvent event) {
        Set<ApplicationStage> list = this.applicationStagesTable.getSelected();
        for (ApplicationStage a : list) {
            this.appStageService.moveUp(a, this.parentApp);
        }
        this.applicationStagesDl.load();
    }

    /**
     * Кнопка "Переместить вниз"
     * @param {Action.ActionPerformedEvent} event
     */
    @Subscribe("applicationStagesTable.movedown")
    private void onApplicationStagesTableMovedown(Action.ActionPerformedEvent event) {
        Set<ApplicationStage> list = this.applicationStagesTable.getSelected();
        for (ApplicationStage a : list) {
            this.appStageService.moveDown(a, this.parentApp);
        }
        this.applicationStagesDl.load();
    }

    @Subscribe
    private void onInit(InitEvent event) {
        // Определяем заявку к которой будут относится отображаемые статьи
        ScreenOptions options = event.getOptions();
        if (options instanceof OptionScreenStage) {
            this.parentApp = (ApplicationIncome) ((OptionScreenStage) options).getApplication();
        }
        this.applicationStagesDl.setParameter("parentApp", this.parentApp.getId());

        // Сортируем статьи по order
        this.appStageService.recalcAppStageOrder(this.parentApp);
    }

    @Subscribe("createBtn")
    private void onCreateBtnClick(Button.ClickEvent event) {
        ApplicationStage applicationStage = new ApplicationStage();
        applicationStage.setApplication(this.parentApp);
        screenBuilders.editor(ApplicationStage.class, this)
                .withScreenClass(ApplicationStageEdit.class)
                .newEntity(applicationStage)
                .withLaunchMode(OpenMode.NEW_WINDOW)
                .build()
                .show();
    }


}
