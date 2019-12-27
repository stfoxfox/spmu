package com.company.spmu.listeners;

import com.company.spmu.entity.Position;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_PositionChangedListener")
public class PositionChangedListener extends AbstractBaseListener<Position> {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Position, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Position> getEntityClass() {
        return Position.class;
    }
}