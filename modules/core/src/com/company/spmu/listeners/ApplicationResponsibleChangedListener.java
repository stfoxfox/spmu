package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationResponsible;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationResponsibleChangedListener")
public class ApplicationResponsibleChangedListener extends AbstractBaseListener<ApplicationResponsible> {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationResponsible, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationResponsible> getEntityClass() {
        return ApplicationResponsible.class;
    }
}