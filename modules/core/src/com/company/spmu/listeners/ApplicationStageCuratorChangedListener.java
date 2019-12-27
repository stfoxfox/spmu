package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationStageCurator;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationStageCuratorChangedListener")
public class ApplicationStageCuratorChangedListener extends AbstractBaseListener<ApplicationStageCurator>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationStageCurator, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationStageCurator> getEntityClass() {
        return ApplicationStageCurator.class;
    }
}