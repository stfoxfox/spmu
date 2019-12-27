package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationObject;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationObjectChangedListener")
public class ApplicationObjectChangedListener extends AbstractBaseListener<ApplicationObject>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationObject, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationObject> getEntityClass() {
        return ApplicationObject.class;
    }
}