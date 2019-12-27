package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@PublishEntityChangedEvents
@Table(name = "SPMU_APPLICATION_OBJECT_STAGE")
@Entity(name = "spmu_ApplicationObjectStage")
public class ApplicationObjectStage extends StandardEntity {
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OBJECT_ID")
    protected ApplicationObject object;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STAGE_ID")
    protected ApplicationStage stage;

    @NotNull
    @Column(name = "ORDER_", nullable = false)
    protected Integer order;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public ApplicationStage getStage() {
        return stage;
    }

    public void setStage(ApplicationStage stage) {
        this.stage = stage;
    }

    public ApplicationObject getObject() {
        return object;
    }

    public void setObject(ApplicationObject object) {
        this.object = object;
    }
}