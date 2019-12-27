package com.company.spmu.listeners;

import com.company.spmu.entity.Measure;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_MeasureChangedListener")
public class MeasureChangedListener extends AbstractBaseListener<Measure>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Measure, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Measure> getEntityClass() {
        return Measure.class;
    }
}