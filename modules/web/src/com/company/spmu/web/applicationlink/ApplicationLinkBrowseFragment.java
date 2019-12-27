package com.company.spmu.web.applicationlink;

import com.company.spmu.entity.*;
import com.company.spmu.enumeration.LinkType;
import com.company.spmu.repositories.entity.ApplicationEventRepository;
import com.company.spmu.repositories.entity.ApplicationStageRepository;
import com.company.spmu.repositories.entity.ApplicationStageValueRepository;
import com.company.spmu.service.AppEventService;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.CreateAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.Target;

import javax.inject.Inject;
import java.util.GregorianCalendar;


@UiController("spmu_ApplicationLinkBrowseFragment")
@UiDescriptor("application-link-browse-fragment.xml")
public class ApplicationLinkBrowseFragment extends ScreenFragment {

    private ApplicationEvent parentApp;
    @Inject
    private CollectionLoader<ApplicationLink> applicationLinksDl;
    @Inject
    private Actions actions;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionContainer<ApplicationLink> applicationLinksDc;
    @Inject
    private Table<ApplicationLink> table;
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionLoader<ApplicationStageEvent> applicationStagesDl;
    @Inject
    private EntityStates entityStates;
    @Inject
    private Notifications notifications;
    @Inject
    private AppEventService appEventService;

    /**
     * Загрузить статьи во вкладке
     * @param parent
     */
    public void setParentApp(ApplicationEvent parent) {
        this.parentApp = parent;

        this.applicationLinksDl.setParameter("parentApp", this.parentApp.getId());
        this.applicationStagesDl.setParameter("parentApp", this.parentApp.getId());
        this.applicationLinksDl.load();
        this.applicationStagesDl.load();
    }

    @Subscribe
    protected void onInit(InitEvent event) {
        CreateAction createAction = (CreateAction) actions.create(CreateAction.ID);
        createAction.withHandler(actionPerformedEvent -> {
            ApplicationLink applicationLink = metadata.create(ApplicationLink.class);
            applicationLink.setParent(parentApp);
            applicationLinksDc.getMutableItems().add(applicationLink);
            table.getItems().updateItem(applicationLink);
        });
        table.addAction(createAction);
    }

    @Subscribe("table.remove")
    private void onTableRemove(Action.ActionPerformedEvent event) {
        ApplicationStage applicationStage = this.table.getSingleSelected().getParentStage();
        this.dataManager.remove(this.table.getSingleSelected());
        this.appEventService.recalculationLinks(applicationStage);
        this.applicationLinksDl.load();
    }

    @Subscribe("saveBtn")
    private void onSaveBtnClick(Button.ClickEvent event) {
        table.getItems().getItems().forEach(entity ->{
            if(entityStates.isNew(entity)){
                if(entity.getParentStage() == null  ){
                    notifications.create().withCaption("Заполните поле \"Наименование этапа\".").show();
                    return;
                }
                if( entity.getChildStage() == null ){
                    notifications.create().withCaption("Заполните поле \"Свявзываемый объект\".").show();
                    return;
                }
                if(entity.getLinkType() == null ){
                    notifications.create().withCaption("Заполните поле \"Тип связи\".").show();
                    return;
                }
                if(entity.getStep() == null){
                    notifications.create().withCaption("Заполните поле \"Сдвиг\".").show();
                    return;
                }
                switch (entity.getLinkType()) {
                    case START_START: {
                        if (entity.getParentStage().getStart().getYear() != entity.getChildStage().getStart().getYear()) {
                            notifications.create().withCaption("Год начала связываемого этапа должен совпадать с годом начала текушего этапа").show();
                            return;
                        }
                        break;
                    }
                    case START_END: {
                        if (entity.getParentStage().getStart().getYear() != entity.getChildStage().getEnd().getYear()) {
                            notifications.create().withCaption("Год начала связываемого этапа должен совпадать с годом окончания текушего этапа").show();
                            return;
                        }
                        break;
                    }
                    case END_END: {
                        if (entity.getParentStage().getEnd().getYear() != entity.getChildStage().getEnd().getYear()){
                            notifications.create().withCaption("Год окончания связываемого этапа должен совпадать с годом окончания текушего этапа").show();
                            return;
                        }
                        break;
                    }
                    default: {
                        notifications.create().withCaption("Неверно указаны параметры").show();
                        return;
                    }
                }

                if(appEventService.change(entity.getParentStage(), entity.getChildStage(), entity.getStep(), entity.getLinkType())){
                    entityStates.makeDetached(entity);
                    dataManager.commit(entity);
                } else {
                    notifications.create().withCaption("Неверно указаны параметры").show();
                }

            }
        });
        applicationLinksDl.load();
    }



}