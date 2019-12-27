package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.security.entity.FilterEntity;

import javax.persistence.*;

@Table(name = "SPMU_PLAN_VARIANT_FILTER")
@Entity(name = "spmu_PlanVariantFilter")
public class PlanVariantFilter extends StandardEntity {
    /**
     * шаблон варианта плана
     * @see PlanVariantTemplate
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_VARIANT_TEMPLATE_ID")
    protected PlanVariantTemplate planVariantTemplate;

    /**
     * фильтр
     * @see FilterEntity
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILTER_ID")
    protected FilterEntity filter;

    public void setPlanVariantTemplate(PlanVariantTemplate planVariantTemplate) {
        this.planVariantTemplate = planVariantTemplate;
    }

    public PlanVariantTemplate getPlanVariantTemplate() {
        return planVariantTemplate;
    }

    public FilterEntity getFilter() {
        return filter;
    }

    public void setFilter(FilterEntity filter) {
        this.filter = filter;
    }
}