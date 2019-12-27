package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationStageContract;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationStageContractChangedListener")
public class ApplicationStageContractChangedListener extends AbstractBaseListener<ApplicationStageContract> {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationStageContract, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ApplicationStageContract> getEntityClass() {
        return ApplicationStageContract.class;
    }
}