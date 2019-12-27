package com.company.spmu.listeners;

import com.company.spmu.entity.Location;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_LocationChangedListener")
public class LocationChangedListener extends AbstractBaseListener<Location>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Location, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Location> getEntityClass() {
        return Location.class;
    }
}