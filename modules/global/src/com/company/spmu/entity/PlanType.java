package com.company.spmu.entity;

import com.company.spmu.entity.interfaces.HasGuid;
import com.company.spmu.enumeration.Kind;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * Типы планов
 */
@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SPMU_PLAN_TYPE")
@Entity(name = "spmu_PlanType")
public class PlanType extends StandardEntity implements HasGuid{
    @NotNull
    @Column(name = "GUID", nullable = false)
    protected UUID guid;

    @NotNull
    @Column(name = "CODE", nullable = false, length = 32)
    protected String code;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    @NotNull
    @Column(name = "KIND", nullable = false)
    protected String kind;

    @NotNull
    @Column(name = "FIXED", nullable = false)
    protected Boolean fixed = false;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FUNDING_ID")
    protected Finsource funding;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @Column(name = "CREATED", nullable = false)
    protected Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @Column(name = "EXPIRED", nullable = false)
    protected Date expired;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CREATOR_ID")
    protected SpmuUser creator;

    public void setKind(Kind kind) {
        this.kind = kind == null ? null : kind.getId();
    }

    public Kind getKind() {
        return kind == null ? null : Kind.fromId(kind);
    }

    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

    public Boolean getFixed() {
        return fixed;
    }

    public SpmuUser getCreator() {
        return creator;
    }

    public void setCreator(SpmuUser creator) {
        this.creator = creator;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Finsource getFunding() {
        return funding;
    }

    public void setFunding(Finsource funding) {
        this.funding = funding;
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