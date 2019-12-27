package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationStageExpense;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationStageExpenseChangedListener")
public class ApplicationStageExpenseChangedListener extends AbstractBaseListener<ApplicationStageExpense>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationStageExpense, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationStageExpense> getEntityClass() {
        return ApplicationStageExpense.class;
    }
}