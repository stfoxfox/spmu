package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationDetail;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationDetailChangedListener")
public class ApplicationDetailChangedListener extends AbstractBaseListener<ApplicationDetail>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationDetail, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationDetail> getEntityClass() {
        return ApplicationDetail.class;
    }
}