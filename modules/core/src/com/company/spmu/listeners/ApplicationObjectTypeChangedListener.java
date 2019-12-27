package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationObjectType;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationObjectTypeChangedListener")
public class ApplicationObjectTypeChangedListener extends AbstractBaseListener<ApplicationObjectType>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationObjectType, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationObjectType> getEntityClass() {
        return ApplicationObjectType.class;
    }
}