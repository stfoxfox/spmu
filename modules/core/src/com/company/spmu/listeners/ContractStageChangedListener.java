package com.company.spmu.listeners;

import com.company.spmu.entity.ContractStage;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ContractStageChangedListener")
public class ContractStageChangedListener extends AbstractBaseListener<ContractStage>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ContractStage, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<ContractStage> getEntityClass() {
        return ContractStage.class;
    }
}