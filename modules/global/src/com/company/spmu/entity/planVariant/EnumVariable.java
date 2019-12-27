package com.company.spmu.entity.planVariant;

import com.company.spmu.entity.planVariant.interfaces.IVariable;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;

@Table(name = "SPMU_ENUM_VARIABLE")
@Entity(name = "spmu_EnumVariable")
public class EnumVariable extends StandardEntity implements IVariable {
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPUTED_VARIABLE_ID")
    protected ComputedVariable computedVariable;

    @Column(name = "TYPE")
    protected String type;

    @Column(name = "POSITION")
    protected Integer position;

    public void setComputedVariable(ComputedVariable computedVariable) {
        this.computedVariable = computedVariable;
    }

    public ComputedVariable getComputedVariable() {
        return computedVariable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Object getValue() {
        return null;
    }


    @Override
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}