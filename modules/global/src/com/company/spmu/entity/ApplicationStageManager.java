package com.company.spmu.entity;

import com.company.spmu.enumeration.ApplicationResponsibleRole;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@PublishEntityChangedEvents
@Entity(name = "spmu_ApplicationStageManager")
@DiscriminatorValue(value="M")
public class ApplicationStageManager extends ApplicationStageResponsible {
    private static final long serialVersionUID = -1260288275054554990L;

    private void init() {
        this.setRole(ApplicationResponsibleRole.MANAGER);
    }

    public ApplicationStageManager() {
        super();
        this.init();
    }

    /**
     * TODO: возможно нужно организовать поиск уже существующей записи
     */
    public static ApplicationStageManager getNew(ApplicationStage a, Employee e) {
        ApplicationStageManager manager = new ApplicationStageManager();
        manager.setApplicationStage(a);
        manager.setEmployee(e);
        return manager;
    }

}
