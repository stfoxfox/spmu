package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;
import com.haulmont.cuba.core.global.DeletePolicy;
import javax.validation.constraints.NotNull;
import javax.persistence.*;

/**
 * заявки в шаблоне варианта плана
 */
@PublishEntityChangedEvents
@Table(name = "SPMU_PLAN_VARIANT_TEMPLATE_APPLICATION")
@Entity(name = "spmu_PlanVariantTemplateApplication")
public class PlanVariantTemplateApplication extends StandardEntity {

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
     * заявка
     * @see PlanVariantTemplate
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "APPLICATION_TEMPLATE_ID")
    protected Application application;

    /**
     * признак заявки в плане
     */
    @NotNull
    @Column(name = "IN_PLAN", nullable = false)
    protected Boolean inPlan;

    public void setApplication(Application application) {
        this.application = application;
    }

    public PlanVariantTemplate getPlanVariantTemplate() {
        return planVariantTemplate;
    }

    public Application getApplication() {
        return application;
    }

    public void setPlanVariantTemplate(PlanVariantTemplate planVariantTemplate) {
        this.planVariantTemplate = planVariantTemplate;
    }

    public Boolean getInPlan() {
        return inPlan;
    }

    public void setInPlan(Boolean inPlan) {
        this.inPlan = inPlan;
    }

    public PlanVariantTemplateApplication() {
        super();
        this.inPlan = true;
    }

}