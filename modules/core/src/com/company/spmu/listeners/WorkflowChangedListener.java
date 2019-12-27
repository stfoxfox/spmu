package com.company.spmu.listeners;

import com.company.spmu.entity.Workflow;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_WorkflowChangedListener")
public class WorkflowChangedListener extends AbstractBaseListener<Workflow>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Workflow, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Workflow> getEntityClass() {
        return Workflow.class;
    }
}