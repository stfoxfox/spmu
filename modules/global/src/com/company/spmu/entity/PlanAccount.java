package com.company.spmu.entity;

import com.company.spmu.entity.interfaces.HasGuid;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import java.util.UUID;

/**
 * Справочник кодов управленческого учета (КУУ)
 */
@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SPMU_PLAN_ACCOUNT")
@Entity(name = "spmu_PlanAccount")
public class PlanAccount extends StandardEntity implements HasGuid {
    @Column(name = "GUID")
    protected UUID guid;

    @Column(name = "CODE", length = 32)
    protected String code;

    @Column(name = "NAME")
    protected String name;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTICLE_ID")
    protected PlanArticle article;

    public PlanArticle getArticle() {
        return article;
    }

    public void setArticle(PlanArticle article) {
        this.article = article;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public UUID getGuid() {
        return guid;
    }

    @Override
    public void setGuid(UUID guid) {
        this.guid = guid;
    }
}