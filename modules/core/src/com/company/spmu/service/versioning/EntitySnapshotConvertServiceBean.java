package com.company.spmu.service.versioning;

import com.company.spmu.entity.ApplicationEvent;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.app.serialization.EntitySerializationAPI;
import com.haulmont.cuba.core.entity.EntitySnapshot;
import com.haulmont.cuba.core.global.ExtendedEntities;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static com.haulmont.cuba.core.app.serialization.EntitySerializationOption.SERIALIZE_INSTANCE_NAME;

@Service(EntitySnapshotConvertService.NAME)
public class EntitySnapshotConvertServiceBean<T> implements EntitySnapshotConvertService {
    @Inject
    protected ExtendedEntities extendedEntities;
    @Inject
    private EntitySerializationAPI entitySerializationAPI;
    @Inject
    protected Metadata metadata;

    @Override
    public T convert(EntitySnapshot entitySnapshot, Class c) {
        String snapshotXml = entitySnapshot.getSnapshotXml();
        MetaClass metaClass = extendedEntities.getOriginalOrThisMetaClass(metadata.getClass(c));
        return entitySerializationAPI.entityFromJson(snapshotXml, metaClass, SERIALIZE_INSTANCE_NAME);
    }
}