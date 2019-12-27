package com.company.spmu.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseLongIdEntity;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MetaClass(name = "spmu_HierarchyApplicationIncome")
public class HierarchyApplicationIncome extends BaseLongIdEntity {
    private static final long serialVersionUID = -8482257481929464165L;

    @MetaProperty
    protected ApplicationIncome app;

    @MetaProperty
    protected ApplicationStageIncome stage;

    @NotNull
    @MetaProperty(mandatory = true)
    protected HierarchyApplicationIncome parent;

    @MetaProperty
    protected String name = "";

    @MetaProperty
    protected String affiliate = "";

    @MetaProperty
    protected String planArticle = "";

    @MetaProperty
    @Temporal(TemporalType.DATE)
    protected Date dateStart;

    @MetaProperty
    @Temporal(TemporalType.DATE)
    protected Date dateEnd;

    @MetaProperty
    protected Double value = 0.0;

    public HierarchyApplicationIncome getParent() {
        return parent;
    }

    public void setParent(HierarchyApplicationIncome parent) {
        this.parent = parent;
    }

    public ApplicationStageIncome getStage() {
        return stage;
    }

    public void setStage(ApplicationStageIncome stage) {
        this.stage = stage;
    }

    public ApplicationIncome getApp() {
        return app;
    }

    public void setApp(ApplicationIncome app) {
        this.app = app;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(String affiliate) {
        this.affiliate = affiliate;
    }

    public String getPlanArticle() {
        return planArticle;
    }

    public void setPlanArticle(String planArticle) {
        this.planArticle = planArticle;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
