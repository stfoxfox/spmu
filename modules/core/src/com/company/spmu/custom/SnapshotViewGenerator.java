package com.company.spmu.custom;

import com.company.spmu.entity.*;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.global.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@Component
public class SnapshotViewGenerator {
    private static final Logger log = LoggerFactory.getLogger(SnapshotViewGenerator.class);
    private static final String SNAPSHOT_VIEW_NAME_POSTFIX = "-view-spanshot";
    @Inject
    protected Metadata metadata;
    @Inject
    protected CustomViewRepository customViewRepository;

    /**
     * Generates entity snapshot views for all application entities.
     * Please add new entity class here once created.
     */
    public void generateSnapshotsViews() {

        List<Class> classes = Arrays.asList(Account.class, Affilate.class, Ano.class, Application.class,
                ApplicationCurator.class, ApplicationDetail.class, ApplicationDetailType.class, ApplicationEditor.class,
                ApplicationEvent.class, ApplicationExpense.class, ApplicationFileDescriptor.class, ApplicationIncome.class,
                ApplicationInitiator.class, ApplicationLink.class, ApplicationManager.class, ApplicationObject.class,
                ApplicationObjectStage.class, ApplicationObjectType.class, ApplicationResponsible.class, ApplicationStage.class,
                ApplicationStageContract.class, ApplicationStageCurator.class, ApplicationStageEvent.class, ApplicationStageExpense.class,
                ApplicationStageFileDescriptor.class, ApplicationStageIncome.class, ApplicationStageManager.class,
                ApplicationStageResponsible.class, ApplicationStageType.class, ApplicationStageValue.class, Coefficient.class,
                CoefficientValue.class, ConnectionLog.class, Contract.class, ContractStage.class, Currency.class, CurrencyRate.class,
                Division.class, Employee.class, EquipmentObject.class, EventType.class, Finsource.class, FixedAsset.class,
                FlyghtType.class, Location.class, Mandate.class, Measure.class, MessageLog.class, Organization.class,
                PlanAccount.class, PlanArticle.class, PlanSection.class, PlanType.class, Position.class, PricingProcedure.class,
                Priority.class, Registry.class, RegistryContent.class, SpmuUser.class, StatusLink.class, Target.class, Workflow.class);

        classes.forEach(this::createAndStoreView);

        log.info("Views for snapshots generated.");
    }

    private void createAndStoreView(Class entityClass) {
        MetaClass classNN = metadata.getClassNN(entityClass);
        customViewRepository.createAndStoreView(entityClass, getSnapshotViewName(classNN));
    }

    private String getSnapshotViewName(MetaClass meta) {
        return meta.getJavaClass().getSimpleName() + SNAPSHOT_VIEW_NAME_POSTFIX;
    }

}
