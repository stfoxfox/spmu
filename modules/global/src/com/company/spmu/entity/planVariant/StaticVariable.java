package com.company.spmu.entity.planVariant;

import com.company.spmu.entity.planVariant.interfaces.IVariable;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;

@Table(name = "SPMU_STATIC_VARIABLE")
@Entity(name = "spmu_StaticVariable")
public class StaticVariable extends StandardEntity implements IVariable {
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPUTED_VARIABLE_ID")
    protected ComputedVariable computedVariable;

    @Column(name = "VALUE")
    protected String value;

    @Column(name = "POSITION")
    protected Integer position;

    public void setComputedVariable(ComputedVariable computedVariable) {
        this.computedVariable = computedVariable;
    }

    public ComputedVariable getComputedVariable() {
        return computedVariable;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}