package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationDetailType;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationDetailTypeChangedListener")
public class ApplicationDetailTypeChangedListener extends AbstractBaseListener<ApplicationDetailType>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationDetailType, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationDetailType> getEntityClass() {
        return ApplicationDetailType.class;
    }
}