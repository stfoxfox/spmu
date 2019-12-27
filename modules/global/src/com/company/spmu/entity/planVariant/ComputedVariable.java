package com.company.spmu.entity.planVariant;

import com.company.spmu.entity.ApplicationEditor;
import com.company.spmu.entity.PlanVariantTemplate;
import com.company.spmu.entity.planVariant.interfaces.IVariable;
import com.company.spmu.enumeration.AggregateFunction;
import com.company.spmu.enumeration.Kind;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import java.util.List;

@Table(name = "SPMU_COMPUTED_VARIABLE")
@Entity(name = "spmu_ComputedVariable")
public class ComputedVariable extends StandardEntity implements IVariable {
    /**
     * шаблон варианта плана
     * @see PlanVariantTemplate
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_VARIANT_TEMPLATE_ID")
    protected PlanVariantTemplate planVariantTemplate;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "AGGREGATE_FUNCTION")
    protected String aggregateFunction;

    @Transient
    protected List<IVariable> variableList;

    public PlanVariantTemplate getPlanVariantTemplate() {
        return planVariantTemplate;
    }

    public void setPlanVariantTemplate(PlanVariantTemplate planVariantTemplate) {
        this.planVariantTemplate = planVariantTemplate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<IVariable> getVariableList() {
        return variableList;
    }

    public void setVariableList(List<IVariable> variableList) {
        this.variableList = variableList;
    }

    public AggregateFunction getAggregateFunction() {
        return aggregateFunction == null ? null : AggregateFunction.fromId(aggregateFunction);
    }

    public void setAggregateFunction(AggregateFunction aggregateFunction) {
        this.aggregateFunction = aggregateFunction == null ? null : aggregateFunction.getId();
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public Integer getPosition() {
        return 0;
    }
}