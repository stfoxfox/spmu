package com.company.spmu.web.applicationhistory;

import com.company.spmu.entity.Application;
import com.company.spmu.entity.bpm.ApplicationHistory;
import com.company.spmu.service.versioning.EntitySnapshotConvertService;
import com.haulmont.cuba.core.entity.EntitySnapshot;
import com.haulmont.cuba.core.global.DatatypeFormatter;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.util.UUID;

@UiController("spmu_ApplicationHistory.browse")
@UiDescriptor("application-history-browse.xml")
@LookupComponent("applicationHistoriesTable")
@LoadDataBeforeShow
public class ApplicationHistoryBrowse extends StandardLookup<ApplicationHistory> {
    @Inject
    private CollectionLoader<ApplicationHistory> applicationHistoriesDl;
    @Inject
    private GroupTable<ApplicationHistory> applicationHistoriesTable;
    @Inject
    protected EntitySnapshotConvertService<Application> entitySnapshotConvertService;

    @Inject
    private DatatypeFormatter formatter;
    @Inject
    private Messages messages;
    @Inject
    private MessageBundle messageBundle;

    @Inject
    protected Label titleLabel;

    private Application application;

    public void setApplication(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    @Subscribe
    private void onInit(BeforeShowEvent event) {
        UUID value = (application != null) ? application.getId() : null;

        this.applicationHistoriesDl.setParameter("entityId", value);
        this.applicationHistoriesDl.load();

        this.titleLabel.setValue(messages.formatMessage(messageBundle.getMessagesPack(), resolveMessageKey(),
                new Object[]{application.getCode() != null ? application.getCode() : "",
                        formatter.formatDate(application.getDate())}));
    }

    private String resolveMessageKey() {
        return "browse.title." + application.getClass().getSimpleName();
    }

    @Subscribe
    private void addFormatterToApplicationStatus(BeforeShowEvent event) {
        Table.Column<ApplicationHistory> entitySnapshot = applicationHistoriesTable.getColumn("entitySnapshot");
        entitySnapshot.setFormatter(o -> {
            Application entity = entitySnapshotConvertService.convert((EntitySnapshot) o, application.getClass());
            return messages.getMessage(entity.getStatus());
        });
    }
}