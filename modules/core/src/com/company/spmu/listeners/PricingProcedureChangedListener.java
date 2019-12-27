package com.company.spmu.listeners;

import com.company.spmu.entity.PricingProcedure;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_PricingProcedureChangedListener")
public class PricingProcedureChangedListener extends AbstractBaseListener<PricingProcedure>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<PricingProcedure, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<PricingProcedure> getEntityClass() {
        return PricingProcedure.class;
    }
}