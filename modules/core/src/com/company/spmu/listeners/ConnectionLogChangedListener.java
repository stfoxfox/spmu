package com.company.spmu.listeners;

import com.company.spmu.entity.ConnectionLog;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ConnectionLogChangedListener")
public class ConnectionLogChangedListener extends AbstractBaseListener<ConnectionLog>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ConnectionLog, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ConnectionLog> getEntityClass() {
        return ConnectionLog.class;
    }
}