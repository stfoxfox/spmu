package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationStage;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationStageChangedListener")
public class ApplicationStageChangedListener extends AbstractBaseListener<ApplicationStage>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationStage, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationStage> getEntityClass() {
        return ApplicationStage.class;
    }
}