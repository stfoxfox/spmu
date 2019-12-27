package com.company.spmu.listeners;

import com.company.spmu.entity.Division;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_DivisionChangedListener")
public class DivisionChangedListener extends AbstractBaseListener<Division>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Division, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Division> getEntityClass() {
        return Division.class;
    }
}