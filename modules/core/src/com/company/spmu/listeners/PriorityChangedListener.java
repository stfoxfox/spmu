package com.company.spmu.listeners;

import com.company.spmu.entity.Priority;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_PriorityChangedListener")
public class PriorityChangedListener extends AbstractBaseListener<Priority>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Priority, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Priority> getEntityClass() {
        return Priority.class;
    }
}