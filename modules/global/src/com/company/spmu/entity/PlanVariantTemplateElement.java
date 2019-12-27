package com.company.spmu.entity;

import com.company.spmu.enumeration.PageElement;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;

/**
 * значение элементов формы представления варианта плана
 */
@Table(name = "SPMU_PLAN_VARIANT_TEMPLATE_ELEMENT")
@Entity(name = "spmu_PlanVariantTemplateElement")
public class PlanVariantTemplateElement extends StandardEntity {
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
     * элементы формы
     */
    @Column(name = "PAGE_ELEMENT")
    protected String pageElement = "";

    @Column(name = "ATTR_NAME")
    protected String attributeName;

    @Column(name = "VAR_VALUE")
    protected String variableValue;

    public PlanVariantTemplate getPlanVariantTemplate() {
        return planVariantTemplate;
    }

    public void setPlanVariantTemplate(PlanVariantTemplate planVariantTemplate) {
        this.planVariantTemplate = planVariantTemplate;
    }

    public void setPageElement(PageElement pageElement) {
        this.pageElement = pageElement == null ? null : pageElement.getId();
    }

    public PageElement getPageElement() {
        return pageElement == null ? null : PageElement.fromId(pageElement);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }

}
