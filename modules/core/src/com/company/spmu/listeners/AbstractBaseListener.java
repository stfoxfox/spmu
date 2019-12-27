package com.company.spmu.listeners;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.app.EntitySnapshotService;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;

import javax.inject.Inject;
import java.util.UUID;

public abstract class AbstractBaseListener<T extends StandardEntity> {
    @Inject
    private Persistence persistence;
    @Inject
    protected Metadata metadata;
    @Inject
    private EntitySnapshotService entitySnapshotService;


    protected void perform(UUID uuid) {
        try (Transaction tx = this.persistence.createTransaction()) {
            EntityManager entityManager = this.persistence.getEntityManager();
            View view = getView();
            T entity = getEntity(uuid, entityManager, view);

            entitySnapshotService.createSnapshot(entity, view);
            tx.commit();
        }
    }

    private T getEntity(UUID id, EntityManager entityManager, View view) {
        return entityManager.find(getEntityClass(), id, view);
    }

    private View getView() {
        return metadata.getViewRepository().getView(getEntityClass(), getViewName(getEntityClass()));
    }

    protected abstract Class<T> getEntityClass();

    protected String getViewName(Class className) {
        return className.getSimpleName() + "-view-spanshot";
    }
}
