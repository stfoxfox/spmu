package com.company.spmu.listeners;

import com.company.spmu.entity.Contract;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ContractChangedListener")
public class ContractChangedListener extends AbstractBaseListener<Contract>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Contract, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Contract> getEntityClass() {
        return Contract.class;
    }
}