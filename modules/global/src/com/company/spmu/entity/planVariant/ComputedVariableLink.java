package com.company.spmu.entity.planVariant;

import com.company.spmu.entity.planVariant.interfaces.IVariable;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;

@Table(name = "SPMU_COMPUTED_VARIABLE_LINK")
@Entity(name = "spmu_ComputedVariableLink")
public class ComputedVariableLink extends StandardEntity implements IVariable {
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPUTED_VARIABLE_ID")
    protected ComputedVariable computedVariable;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPUTED_VARIABLE_CHILD_ID")
    protected ComputedVariable computedVariableChild;

    @Column(name = "POSITION")
    protected Integer position;

    public ComputedVariable getComputedVariable() {
        return computedVariable;
    }

    public void setComputedVariable(ComputedVariable computedVariable) {
        this.computedVariable = computedVariable;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    @Override
    public Object getValue() {
        return null;
    }

    public ComputedVariable getComputedVariableChild() {
        return computedVariableChild;
    }

    public void setComputedVariableChild(ComputedVariable computedVariableChild) {
        this.computedVariableChild = computedVariableChild;
    }
}