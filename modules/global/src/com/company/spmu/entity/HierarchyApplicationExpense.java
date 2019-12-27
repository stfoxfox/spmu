package com.company.spmu.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseLongIdEntity;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MetaClass(name = "spmu_HierarchyApplicationExpense")
public class HierarchyApplicationExpense extends BaseLongIdEntity {
    private static final long serialVersionUID = -361290896499556875L;

    @MetaProperty
    protected ApplicationExpense app;

    @MetaProperty
    protected ApplicationStageExpense stage;

    @NotNull
    @MetaProperty(mandatory = true)
    protected HierarchyApplicationExpense parent;

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

    public HierarchyApplicationExpense getParent() {
        return parent;
    }

    public void setParent(HierarchyApplicationExpense parent) {
        this.parent = parent;
    }

    public ApplicationStageExpense getStage() {
        return stage;
    }

    public void setStage(ApplicationStageExpense stage) {
        this.stage = stage;
    }

    public ApplicationExpense getApp() {
        return app;
    }

    public void setApp(ApplicationExpense app) {
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
