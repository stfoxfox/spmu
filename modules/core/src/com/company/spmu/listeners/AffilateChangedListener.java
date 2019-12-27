package com.company.spmu.listeners;

import com.company.spmu.entity.Affilate;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_AffilateChangedListener")
public class AffilateChangedListener extends AbstractBaseListener<Affilate>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Affilate, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Affilate> getEntityClass() {
        return Affilate.class;
    }
}