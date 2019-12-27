package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationStageType;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationStageTypeChangedListener")
public class ApplicationStageTypeChangedListener extends AbstractBaseListener<ApplicationStageType>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationStageType, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationStageType> getEntityClass() {
        return ApplicationStageType.class;
    }
}