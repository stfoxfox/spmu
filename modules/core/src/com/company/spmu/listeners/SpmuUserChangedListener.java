package com.company.spmu.listeners;

import com.company.spmu.entity.SpmuUser;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_SpmuUserChangedListener")
public class SpmuUserChangedListener extends AbstractBaseListener<SpmuUser>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<SpmuUser, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<SpmuUser> getEntityClass() {
        return SpmuUser.class;
    }
}