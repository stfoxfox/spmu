package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.Entity;
@PublishEntityChangedEvents
@Entity(name = "spmu_ApplicationStageEvent")
public class ApplicationStageEvent extends ApplicationStage {
}
