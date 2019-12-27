package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationEvent;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationEventChangedListener")
public class ApplicationEventChangedListener extends AbstractBaseListener<ApplicationEvent> {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationEvent, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationEvent> getEntityClass() {
        return ApplicationEvent.class;
    }

}