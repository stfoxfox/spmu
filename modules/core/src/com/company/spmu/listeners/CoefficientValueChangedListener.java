package com.company.spmu.listeners;

import com.company.spmu.entity.CoefficientValue;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_CoefficientValueChangedListener")
public class CoefficientValueChangedListener extends AbstractBaseListener<CoefficientValue>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<CoefficientValue, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<CoefficientValue> getEntityClass() {
        return CoefficientValue.class;
    }
}