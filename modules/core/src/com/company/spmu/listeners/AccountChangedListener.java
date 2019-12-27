package com.company.spmu.listeners;

import com.company.spmu.entity.Account;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_AccountChangedListener")
public class AccountChangedListener extends AbstractBaseListener<Account> {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Account, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Account> getEntityClass() {
        return Account.class;
    }
}