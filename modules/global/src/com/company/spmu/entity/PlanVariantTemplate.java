package com.company.spmu.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * конструктор/шаблон варианта плана
 */
@NamePattern("%s|name")
@Table(name = "SPMU_PLAN_VARIANT_TEMPLATE")
@Entity(name = "spmu_PlanVariantTemplate")
@PublishEntityChangedEvents
public class PlanVariantTemplate extends StandardEntity {
    /**
     * вариант плана
     *
     * @see PlanVariant
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_VARIANT_ID")
    protected PlanVariant planVariant;

    /**
     * название
     */
    @Column(name = "NAME")
    protected String name;

    /**
     * текущий шаблон для варианта плана
     */
    @NotNull
    @Column(name = "ACTIVE", nullable = false)
    protected Boolean active = false;

    @MetaProperty
    @Composition
    @OneToMany(mappedBy = "planVariantTemplate")
    protected List<PlanVariantTemplateElement> elements;

    public void setPlanVariant(PlanVariant planVariant) {
        this.planVariant = planVariant;
    }

    public PlanVariant getPlanVariant() {
        return planVariant;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<PlanVariantTemplateElement> getElements() {
        return elements;
    }

    public void setElements(List<PlanVariantTemplateElement> elements) {
        this.elements = elements;
    }

}
