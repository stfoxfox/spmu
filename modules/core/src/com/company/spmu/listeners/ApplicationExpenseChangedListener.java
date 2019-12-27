package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationExpense;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationExpenseChangedListener")
public class ApplicationExpenseChangedListener extends AbstractBaseListener<ApplicationExpense>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationExpense, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationExpense> getEntityClass() {
        return ApplicationExpense.class;
    }
}