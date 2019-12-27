package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
@PublishEntityChangedEvents
@Entity(name = "spmu_ApplicationStageIncome")
@DiscriminatorColumn()
public class ApplicationStageIncome extends ApplicationStage {
    private static final long serialVersionUID = 2552638943143633503L;
}
