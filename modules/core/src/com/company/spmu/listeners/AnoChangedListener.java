package com.company.spmu.listeners;

import com.company.spmu.entity.Ano;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_AnoChangedListener")
public class AnoChangedListener extends AbstractBaseListener<Ano> {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Ano, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Ano> getEntityClass() {
        return Ano.class;
    }
}