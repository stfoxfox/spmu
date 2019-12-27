package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationObjectStage;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationObjectStageChangedListener")
public class ApplicationObjectStageChangedListener extends AbstractBaseListener<ApplicationObjectStage> {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationObjectStage, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationObjectStage> getEntityClass() {
        return ApplicationObjectStage.class;
    }
}