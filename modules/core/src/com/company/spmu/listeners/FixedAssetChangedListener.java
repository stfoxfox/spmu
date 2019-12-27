package com.company.spmu.listeners;

import com.company.spmu.entity.FixedAsset;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_FixedAssetChangedListener")
public class FixedAssetChangedListener extends AbstractBaseListener<FixedAsset>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<FixedAsset, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<FixedAsset> getEntityClass() {
        return FixedAsset.class;
    }
}