package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.Entity;
@PublishEntityChangedEvents
@Entity(name = "spmu_ApplicationStageExpense")
public class ApplicationStageExpense extends ApplicationStage {
    private static final long serialVersionUID = -58610546145879231L;
}
