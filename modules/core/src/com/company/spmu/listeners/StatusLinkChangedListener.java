package com.company.spmu.listeners;

import com.company.spmu.entity.StatusLink;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_StatusLinkChangedListener")
public class StatusLinkChangedListener extends AbstractBaseListener<StatusLink>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<StatusLink, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<StatusLink> getEntityClass() {
        return null;
    }
}