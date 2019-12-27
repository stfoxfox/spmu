package com.company.spmu.listeners;

import com.company.spmu.entity.Organization;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_OrganizationChangedListener")
public class OrganizationChangedListener extends AbstractBaseListener<Organization> {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Organization, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Organization> getEntityClass() {
        return Organization.class;
    }
}