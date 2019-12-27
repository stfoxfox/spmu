package com.company.spmu.service.versioning;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Predicate;

public interface RestoreVersionService {
    String NAME = "spmu_RestoreVersionService";

    @Nullable
    StandardEntity getFirst(Class className, UUID id);
    @Nullable
    StandardEntity getByVersion(Class className, UUID id, int version);
    @Nullable
    StandardEntity getByCondition(Class className, UUID entityId, Predicate<StandardEntity> entityPredicate);
}