package com.company.spmu.entity;

import com.company.spmu.entity.interfaces.HasGuid;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SPMU_PLAN_SECTION")
@Entity(name = "spmu_PlanSection")
public class PlanSection extends StandardEntity implements HasGuid {
    @Column(name = "GUID")
    protected UUID guid;

    @NotNull
    @Column(name = "CODE", nullable = false, length = 32)
    protected String code;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    protected PlanSection parent;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_ID")
    protected PlanType type;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    protected Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRED")
    protected Date expired;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATOR_ID")
    protected SpmuUser creator;

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

    public PlanType getType() {
        return type;
    }

    public void setType(PlanType type) {
        this.type = type;
    }

    public PlanSection getParent() {
        return parent;
    }

    public void setParent(PlanSection parent) {
        this.parent = parent;
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