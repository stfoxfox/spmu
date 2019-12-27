package com.company.spmu.listeners;

import com.company.spmu.entity.EventType;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_EventTypeChangedListener")
public class EventTypeChangedListener extends AbstractBaseListener<EventType>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<EventType, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<EventType> getEntityClass() {
        return EventType.class;
    }
}