package com.company.spmu.listeners;

import com.company.spmu.entity.MessageLog;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_MessageLogChangedListener")
public class MessageLogChangedListener extends AbstractBaseListener<MessageLog> {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<MessageLog, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<MessageLog> getEntityClass() {
        return MessageLog.class;
    }
}