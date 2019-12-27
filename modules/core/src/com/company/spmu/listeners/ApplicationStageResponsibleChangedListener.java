package com.company.spmu.listeners;

import com.company.spmu.entity.ApplicationStageResponsible;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_ApplicationStageResponsibleChangedListener")
public class ApplicationStageResponsibleChangedListener extends AbstractBaseListener<ApplicationStageResponsible>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<ApplicationStageResponsible, UUID> event) {

    }

    @Override
    protected Class<ApplicationStageResponsible> getEntityClass() {
        return ApplicationStageResponsible.class;
    }
}