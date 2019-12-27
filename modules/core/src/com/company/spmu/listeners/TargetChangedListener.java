package com.company.spmu.listeners;

import com.company.spmu.entity.Target;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_TargetChangedListener")
public class TargetChangedListener extends AbstractBaseListener<Target>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Target, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Target> getEntityClass() {
        return Target.class;
    }
}