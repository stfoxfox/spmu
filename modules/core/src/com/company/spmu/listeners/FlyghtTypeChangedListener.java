package com.company.spmu.listeners;

import com.company.spmu.entity.FlyghtType;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_FlyghtTypeChangedListener")
public class FlyghtTypeChangedListener extends AbstractBaseListener<FlyghtType>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<FlyghtType, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<FlyghtType> getEntityClass() {
        return FlyghtType.class;
    }
}