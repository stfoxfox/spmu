package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationFileDescriptor;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationFileDescriptorChangedListener")
public class ApplicationFileDescriptorChangedListener extends AbstractBaseListener<ApplicationFileDescriptor>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationFileDescriptor, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationFileDescriptor> getEntityClass() {
        return ApplicationFileDescriptor.class;
    }
}