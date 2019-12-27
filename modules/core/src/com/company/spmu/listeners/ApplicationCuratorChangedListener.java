package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationCurator;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationCuratorChangedListener")
public class ApplicationCuratorChangedListener extends AbstractBaseListener<ApplicationCurator> {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationCurator, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationCurator> getEntityClass() {
        return ApplicationCurator.class;
    }
}