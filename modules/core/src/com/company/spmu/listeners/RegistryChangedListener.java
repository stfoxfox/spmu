package com.company.spmu.listeners;

import com.company.spmu.entity.Registry;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_RegistryChangedListener")
public class RegistryChangedListener extends AbstractBaseListener<Registry> {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Registry, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Registry> getEntityClass() {
        return Registry.class;
    }
}