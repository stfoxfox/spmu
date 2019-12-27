package com.company.spmu.listeners;

import com.company.spmu.entity.Finsource;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_FinsourceChangedListener")
public class FinsourceChangedListener extends AbstractBaseListener<Finsource>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Finsource, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Finsource> getEntityClass() {
        return Finsource.class;
    }
}