package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationInitiator;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationInitiatorChangedListener")
public class ApplicationInitiatorChangedListener extends AbstractBaseListener<ApplicationInitiator>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationInitiator, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationInitiator> getEntityClass() {
        return ApplicationInitiator.class;
    }
}