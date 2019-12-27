package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@PublishEntityChangedEvents
@Entity(name = "spmu_ApplicationStageCurator")
@DiscriminatorValue(value="C")
public class ApplicationStageCurator extends ApplicationStageResponsible {
    private static final long serialVersionUID = 8569658949666886605L;

    /**
     * TODO: возможно нужно организовать поиск уже существующей записи
     */
    public static ApplicationStageCurator getNew(ApplicationStage a, Employee e) {
        ApplicationStageCurator curator = new ApplicationStageCurator();
        curator.setApplicationStage(a);
        curator.setEmployee(e);
        return curator;
    }

}
