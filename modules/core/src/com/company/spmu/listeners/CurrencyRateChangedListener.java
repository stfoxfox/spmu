package com.company.spmu.listeners;

import com.company.spmu.entity.CurrencyRate;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_CurrencyRateChangedListener")
public class CurrencyRateChangedListener extends AbstractBaseListener<CurrencyRate>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<CurrencyRate, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<CurrencyRate> getEntityClass() {
        return CurrencyRate.class;
    }
}