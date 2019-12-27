package com.company.spmu.listeners;

import com.company.spmu.entity.RegistryContent;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_RegistryContentChangedListener")
public class RegistryContentChangedListener extends AbstractBaseListener<RegistryContent>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<RegistryContent, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<RegistryContent> getEntityClass() {
        return RegistryContent.class;
    }
}