package com.company.spmu.listeners;

import com.company.spmu.entity.PlanAccount;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_PlanAccountChangedListener")
public class PlanAccountChangedListener extends AbstractBaseListener<PlanAccount>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<PlanAccount, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<PlanAccount> getEntityClass() {
        return PlanAccount.class;
    }
}