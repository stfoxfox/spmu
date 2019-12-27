package com.company.spmu.process.helper;

import com.company.spmu.entity.Application;
import com.company.spmu.enumeration.ApplicationStatus;
import com.company.spmu.enumeration.ApplicationType;
import com.company.spmu.service.integration.export.ApplicationEventExportService;
import com.company.spmu.service.integration.IntegrationService;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.UUID;

@Component(ApplicationProcessHelper.NAME)
    public class ApplicationProcessHelper implements IUpdateStatusHelper {
    public static final String NAME = "spmu_ApplicationProcessHelper";
    @Inject
    private Persistence persistence;
    @Inject
    private ApplicationEventExportService applicationEventExportService;
    @Inject
    private IntegrationService integrationService;

    @Override
    public void updateStatus(UUID entityId, String status) {
        try (Transaction tx = persistence.getTransaction()) {
            Application application = persistence.getEntityManager().find(Application.class, entityId);
            if (application != null) {
                application.setStatus(ApplicationStatus.valueOf(status));
            }
            tx.commit();
            if (application.getType() == ApplicationType.EVENT) {
                integrationService.sendData(
                        applicationEventExportService.getExportJsonString(application),
                        ApplicationEventExportService.ADDITIONAL_URL);
            }
        }
    }
}