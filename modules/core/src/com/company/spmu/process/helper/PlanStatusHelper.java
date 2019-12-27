package com.company.spmu.process.helper;

import com.company.spmu.entity.PlanVariant;;
import com.company.spmu.enumeration.PlanVariantStatus;
import com.company.spmu.service.integration.export.ApplicationEventExportService;
import com.company.spmu.service.integration.IntegrationService;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.UUID;

@Component(PlanStatusHelper.NAME)
public class PlanStatusHelper implements IUpdatedStatusHelper {
    public static final String NAME = "spmu_PlanStatusHelper";
    @Inject
    private Persistence persistence;


    @Override
    public void updatedStatus(UUID entityId, String status) {
        try (Transaction tx = persistence.getTransaction()) {
            PlanVariant planVariant = persistence.getEntityManager().find(PlanVariant.class, entityId);
            if (planVariant != null) {
                planVariant.setStatus(PlanVariantStatus.valueOf(status));
            }
            tx.commit();

        }
    }
}