package com.company.spmu.bpm;

import com.company.spmu.custom.SnapshotViewGenerator;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.core.sys.events.AppContextStartedEvent;
import com.haulmont.cuba.security.app.Authenticated;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component(AppLifecycle.NAME)
public class AppLifecycle {
    public static final String NAME = "spmu_AppLifecycle";

    @Inject
    private DesignerModelImporter designerModelImportService;
    @Inject
    private ProcessModelDeployer processModelDeployer;
    @Inject
    private SnapshotViewGenerator snapshotViewGenerator;

    @Authenticated
    @EventListener(AppContextStartedEvent.class)
    @Order(Events.LOWEST_PLATFORM_PRECEDENCE + 100)
    public void setUp() {
        snapshotViewGenerator.generateSnapshotsViews();
        designerModelImportService.importModels();
        processModelDeployer.deployModels();
    }
}