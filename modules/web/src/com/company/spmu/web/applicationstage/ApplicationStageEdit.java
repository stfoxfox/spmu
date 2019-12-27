package com.company.spmu.web.applicationstage;

import com.company.spmu.entity.*;
import com.company.spmu.web.correctionhistory.ApplicationStageCorrectionHistoryFragment;
import com.haulmont.cuba.core.global.DatatypeFormatter;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstancePropertyContainer;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;

@UiController("spmu_ApplicationStage.edit")
@UiDescriptor("application-stage-edit.xml")
@EditedEntityContainer("applicationStageDc")
@LoadDataBeforeShow
public class ApplicationStageEdit extends StandardEditor<ApplicationStage> {
    @Inject
    protected Metadata metadata;

    @Inject
    protected InstancePropertyContainer<ApplicationStageValue> applicationStageValueDc;

    @Inject
    protected InstancePropertyContainer<ApplicationStageContract> applicationStageContractDc;

    @Inject
    protected DataContext dataContext;

    @Inject
    private DatatypeFormatter formatter;

    @Inject
    protected Label titleLabel;

    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe
    protected void onInitEntity(InitEntityEvent<ApplicationStage> event) {

        ApplicationStageValue applicationStageValue = createApplicationStageValue();
        applicationStageValue.setApplicationStage(event.getEntity());
        event.getEntity().setApplicationStageValue(applicationStageValue);

        ApplicationStageContract createApplicationStageContract = createApplicationStageContract();
        createApplicationStageContract.setStage(event.getEntity());
        event.getEntity().setApplicationStageContract(createApplicationStageContract);
    }

    private ApplicationStageValue createApplicationStageValue() {
        return dataContext.merge(metadata.create(ApplicationStageValue.class));
    }

    private ApplicationStageContract createApplicationStageContract() {
        return dataContext.merge(metadata.create(ApplicationStageContract.class));
    }

    @Subscribe
    private void onBeforeCommitChanges() {
        this.getEditedEntity().getApplicationStageValue().setQ1(this.getEditedEntity().getQ1());
        this.getEditedEntity().getApplicationStageValue().setQ2(this.getEditedEntity().getQ2());
        this.getEditedEntity().getApplicationStageValue().setQ3(this.getEditedEntity().getQ3());
        this.getEditedEntity().getApplicationStageValue().setQ4(this.getEditedEntity().getQ4());
    }

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        String title = "Статья заявки на доходы ";
        Application application = this.getEditedEntity().getApplication();
        title += application.getCode();
        title += " от " + formatter.formatDate(application.getDate());

        titleLabel.setValue(title);
    }

    @Subscribe("changesBtn")
    private void onChangesBtnClick(Button.ClickEvent event) {

        ApplicationStageCorrectionHistoryFragment applicationHistoryBrowse = screenBuilders.screen(this)
                .withScreenClass(ApplicationStageCorrectionHistoryFragment.class)
                .withLaunchMode(OpenMode.NEW_TAB)
                .build();

        applicationHistoryBrowse.setEntity(this.getEditedEntity());
        applicationHistoryBrowse.show();
    }
}