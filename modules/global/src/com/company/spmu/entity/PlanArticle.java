package com.company.spmu.entity;

import com.company.spmu.entity.interfaces.HasGuid;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Справочник статей ВПО
 */
@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SPMU_PLAN_ARTICLE")
@Entity(name = "spmu_PlanArticle")
public class PlanArticle extends StandardEntity implements HasGuid {
    @Column(name = "GUID")
    protected UUID guid;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    protected PlanArticle parent;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINSOURCE_ID")
    protected Finsource finsource;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name = "";

    @Lob
    @Column(name = "DESCRIPTION")
    protected String description = "";

    @Column(name = "FULLNAME")
    protected String fullname = "";

    @NotNull
    @Column(name = "CODE", nullable = false)
    protected String code = "";

    public void setParent(PlanArticle parent) {
        this.parent = parent;
    }

    public PlanArticle getParent() {
        return parent;
    }

    public void setFinsource(Finsource finsource) {
        this.finsource = finsource;
    }

    public Finsource getFinsource() {
        return finsource;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public UUID getGuid() {
        return guid;
    }

    @Override
    public void setGuid(UUID guid) {
        this.guid = guid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
