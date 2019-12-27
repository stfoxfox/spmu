package com.company.spmu.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;
import javax.validation.constraints.NotNull;
import javax.persistence.*;

@PublishEntityChangedEvents
@Table(name = "SPMU_PLAN_ARTICLE_STAGE")
@Entity(name = "spmu_PlanArticleStage")
@NamePattern("%s|name")
public class PlanArticleStage extends StandardEntity {
    private static final long serialVersionUID = -6109772207850182581L;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_ARTICLE_ID")
    protected PlanArticle planArticle;

    @Column(name = "NAME", nullable = false)
    @NotNull
    protected String name = "";

    @Column(name = "IS_CHANGE", nullable = false)
    @NotNull
    protected Boolean isChange = true;

    public Boolean getIsChange() {
        return isChange;
    }

    public void setIsChange(Boolean isChange) {
        this.isChange = isChange;
    }

    public String getName() {
        return name;
    }

    public PlanArticle getPlanArticle() {
        return planArticle;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlanArticle(PlanArticle planArticle) {
        this.planArticle = planArticle;
    }
}
