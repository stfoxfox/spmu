package com.company.spmu.listeners;

import com.company.spmu.entity.Coefficient;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_CoefficientChangedListener")
public class CoefficientChangedListener extends AbstractBaseListener<Coefficient>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Coefficient, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Coefficient> getEntityClass() {
        return Coefficient.class;
    }
}