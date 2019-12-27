package com.company.spmu.listeners;

import com.company.spmu.entity.PlanType;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_PlanTypeChangedListener")
public class PlanTypeChangedListener extends AbstractBaseListener<PlanType>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<PlanType, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<PlanType> getEntityClass() {
        return PlanType.class;
    }
}