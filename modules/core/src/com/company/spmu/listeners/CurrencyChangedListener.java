package com.company.spmu.listeners;

import com.company.spmu.entity.Currency;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_CurrencyChangedListener")
public class CurrencyChangedListener extends AbstractBaseListener<Currency>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Currency, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Currency> getEntityClass() {
        return Currency.class;
    }
}