package com.company.spmu.listeners;

import com.company.spmu.entity.EquipmentObject;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_EquipmentObjectChangedListener")
public class EquipmentObjectChangedListener extends AbstractBaseListener<EquipmentObject>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<EquipmentObject, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<EquipmentObject> getEntityClass() {
        return EquipmentObject.class;
    }
}