package com.company.spmu.service.versioning;

import com.company.spmu.entity.Application;
import com.company.spmu.entity.ApplicationEvent;
import com.company.spmu.entity.SpmuUser;
import com.company.spmu.entity.bpm.ApplicationHistory;
import com.company.spmu.repositories.entity.SpmuUserRepository;
import com.company.spmu.repositories.entity.ApplicationEventRepository;
import com.company.spmu.repositories.entity.ApplicationHistoryRepository;
import com.haulmont.bpm.entity.ProcTask;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.app.EntitySnapshotService;
import com.haulmont.cuba.core.app.serialization.EntitySerializationAPI;
import com.haulmont.cuba.core.entity.EntitySnapshot;
import com.haulmont.cuba.core.global.ExtendedEntities;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.haulmont.cuba.core.app.serialization.EntitySerializationOption.SERIALIZE_INSTANCE_NAME;

@Service(ApplicationHistoryService.NAME)
public class ApplicationHistoryServiceBean implements ApplicationHistoryService {

    @Inject
    protected Metadata metadata;
    @Inject
    private EntitySerializationAPI entitySerializationAPI;
    @Inject
    protected ExtendedEntities extendedEntities;
    @Inject
    private EntitySnapshotService entitySnapshotService;
    @Inject
    private ApplicationHistoryRepository applicationHistoryRepository;
    @Inject
    private ApplicationEventRepository applicationEventRepository;
    @Inject
    private SpmuUserRepository userRepository;

    @Override
    public void store(ProcTask procTask) {
        if (procTask == null){
            return;
        }
        performAction(procTask);
    }

    private void performAction(ProcTask procTask) {
        UUID entityId = procTask.getProcInstance().getEntity().getEntityId();
        MetaClass metaClass = extendedEntities.getOriginalOrThisMetaClass(metadata.getClass(ApplicationEvent.class));
        List<ApplicationEvent> applications = applicationEventRepository.findOneById(entityId);

        if (!applications.isEmpty()) {
            ApplicationEvent latestEvent = applications.get(0);
            List<EntitySnapshot> snapshots = entitySnapshotService.getSnapshots(metaClass, entityId);
            Optional<EntitySnapshot> snapshot = snapshots.stream()
                    .filter(entitySnapshot -> isSameSnapshotVersion(latestEvent, metaClass, entitySnapshot))
                    .findFirst();

            if (snapshot.isPresent()) {
                saveApplicationHistory(procTask, snapshot.get());
            }
        }
    }

    private boolean isSameSnapshotVersion(Application application, MetaClass metaClass, EntitySnapshot entitySnapshot) {
        String snapshotXml = entitySnapshot.getSnapshotXml();
        Application entity = entitySerializationAPI.entityFromJson(snapshotXml, metaClass, SERIALIZE_INSTANCE_NAME);
        return entity.getVersion().equals(application.getVersion());
    }

    private void saveApplicationHistory(ProcTask procTask, EntitySnapshot snapshot) {
        ApplicationHistory history = new ApplicationHistory();
        history.setProcTask(procTask);
        history.setEntitySnapshot(snapshot);
        SpmuUser user = userRepository.findOne(procTask.getProcActor().getUser().getId());
        history.setUser(user);

        applicationHistoryRepository.save(history);
    }
}