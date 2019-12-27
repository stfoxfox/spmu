package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationStageManager;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationStageManagerChangedListener")
public class ApplicationStageManagerChangedListener extends AbstractBaseListener<ApplicationStageManager>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationStageManager, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationStageManager> getEntityClass() {
        return ApplicationStageManager.class;
    }
}