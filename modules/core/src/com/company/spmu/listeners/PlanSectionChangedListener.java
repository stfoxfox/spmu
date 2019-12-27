package com.company.spmu.listeners;

import com.company.spmu.entity.PlanSection;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_PlanSectionChangedListener")
public class PlanSectionChangedListener extends AbstractBaseListener<PlanSection>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<PlanSection, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<PlanSection> getEntityClass() {
        return PlanSection.class;
    }
}