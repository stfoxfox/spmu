package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@PublishEntityChangedEvents
@Table(name = "SPMU_COEFFICIENT_VALUE")
@Entity(name = "spmu_CoefficientValue")
public class CoefficientValue extends StandardEntity {
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COEFFICIENT_ID")
    protected Coefficient coefficient;

    @NotNull
    @Column(name = "PERIOD", nullable = false)
    protected Integer period;

    @Column(name = "VALUE")
    protected Double value;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Coefficient getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Coefficient coefficient) {
        this.coefficient = coefficient;
    }
}