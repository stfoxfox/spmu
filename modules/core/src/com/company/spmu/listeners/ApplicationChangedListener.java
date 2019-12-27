package com.company.spmu.listeners;

import com.company.spmu.entity.Application;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationChangedListener")
public class ApplicationChangedListener extends AbstractBaseListener<Application> {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Application, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Application> getEntityClass() {
        return Application.class;
    }
}