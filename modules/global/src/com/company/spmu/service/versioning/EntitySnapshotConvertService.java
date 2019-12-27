package com.company.spmu.service.versioning;

import com.haulmont.cuba.core.entity.EntitySnapshot;

public interface EntitySnapshotConvertService<T> {
    String NAME = "spmu_EntitySnapshotConvertService";

    T convert(EntitySnapshot snapshot, Class c);
}