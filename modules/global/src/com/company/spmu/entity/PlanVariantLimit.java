package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * лимиты варианта плана по годам
 */
@PublishEntityChangedEvents
@Table(name = "SPMU_PLAN_VARIANT_LIMIT")
@Entity(name = "spmu_PlanVariantLimit")
public class PlanVariantLimit extends StandardEntity {
    /**
     * вариант плана
     * @see PlanVariant
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_VARIANT_ID")
    protected PlanVariant planVariant;

    @Column(name = "YEAR")
    protected Integer year;

    /**
     * лимит
     */
    @NotNull
    @Column(name = "VALUE", nullable = false)
    protected Double value = 0.0;

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getYear() {
        return year;
    }

    public PlanVariant getPlanVariant() {
        return planVariant;
    }

    public Double getValue() {
        return value;
    }

    public void setPlanVariant(PlanVariant planVariant) {
        this.planVariant = planVariant;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
