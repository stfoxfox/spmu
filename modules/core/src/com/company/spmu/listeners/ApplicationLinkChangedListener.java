package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationLink;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationLinkChangedListener")
public class ApplicationLinkChangedListener extends AbstractBaseListener<ApplicationLink>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationLink, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationLink> getEntityClass() {
        return ApplicationLink.class;
    }
}