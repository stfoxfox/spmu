package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationIncome;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationIncomeChangedListener")
public class ApplicationIncomeChangedListener extends AbstractBaseListener<ApplicationIncome> {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationIncome, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationIncome> getEntityClass() {
        return ApplicationIncome.class;
    }
}