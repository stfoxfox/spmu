package com.company.spmu.listeners;

import com.company.spmu.entity.PlanArticle;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_PlanArticleChangedListener")
public class PlanArticleChangedListener extends AbstractBaseListener<PlanArticle>{

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<PlanArticle, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<PlanArticle> getEntityClass() {
        return PlanArticle.class;
    }
}