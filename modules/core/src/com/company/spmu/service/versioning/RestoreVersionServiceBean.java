package com.company.spmu.service.versioning;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.app.EntitySnapshotService;
import com.haulmont.cuba.core.app.serialization.EntitySerializationAPI;
import com.haulmont.cuba.core.entity.EntitySnapshot;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.ExtendedEntities;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import static com.haulmont.cuba.core.app.serialization.EntitySerializationOption.SERIALIZE_INSTANCE_NAME;

@Service(RestoreVersionService.NAME)
public class RestoreVersionServiceBean implements RestoreVersionService {

    @Inject
    private EntitySerializationAPI entitySerializationAPI;
    @Inject
    private EntitySnapshotService entitySnapshotService;
    @Inject
    protected ExtendedEntities extendedEntities;
    @Inject
    protected Metadata metadata;

    @Override
    public StandardEntity getFirst(Class entityClass, UUID entityId) {
        return getByVersion(entityClass, entityId, 1);
    }

    @Override
    public StandardEntity getByVersion(Class entityClass, UUID entityId, int version) {
        Predicate<StandardEntity> entityVersionPredicate = entity -> entity.getVersion().equals(version);
        return getByCondition(entityClass, entityId, entityVersionPredicate);
    }

    @Override
    public StandardEntity getByCondition(Class entityClass, UUID entityId, Predicate<StandardEntity> entityPredicate) {
        MetaClass metaClass = extendedEntities.getOriginalOrThisMetaClass(metadata.getClass(entityClass));
        List<EntitySnapshot> snapshots = entitySnapshotService.getSnapshots(metaClass, entityId);

        Optional<StandardEntity> first = snapshots.stream()
                .map(entitySnapshot -> getEntity(metaClass, entitySnapshot))
                .filter(entityPredicate)
                .findFirst();

        return first.isPresent()?first.get():null;
    }

    private StandardEntity getEntity(MetaClass metaClass, EntitySnapshot entitySnapshot) {
        String snapshotXml = entitySnapshot.getSnapshotXml();
        return entitySerializationAPI.entityFromJson(snapshotXml, metaClass, SERIALIZE_INSTANCE_NAME);
    }

}