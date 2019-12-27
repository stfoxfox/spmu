package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationManager;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationManagerChangedListener")
public class ApplicationManagerChangedListener extends AbstractBaseListener<ApplicationManager>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationManager, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationManager> getEntityClass() {
        return ApplicationManager.class;
    }
}