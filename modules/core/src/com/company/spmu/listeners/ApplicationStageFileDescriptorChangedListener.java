package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationStageFileDescriptor;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationStageFileDescriptorChangedListener")
public class ApplicationStageFileDescriptorChangedListener extends AbstractBaseListener<ApplicationStageFileDescriptor>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationStageFileDescriptor, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationStageFileDescriptor> getEntityClass() {
        return ApplicationStageFileDescriptor.class;
    }
}