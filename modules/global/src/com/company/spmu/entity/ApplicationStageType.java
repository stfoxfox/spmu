package com.company.spmu.entity;

import com.company.spmu.enumeration.Calendar;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SPMU_APPLICATION_STAGE_TYPE")
@Entity(name = "spmu_ApplicationStageType")
public class ApplicationStageType extends StandardEntity {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    protected Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRED")
    protected Date expired;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CREATOR_ID")
    protected SpmuUser creator;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "FRACTION")
    protected Double fraction;

    @Column(name = "DURATION")
    protected Integer duration;

    @Column(name = "CALENDAR")
    protected String calendar;

    public Calendar getCalendar() {
        return calendar == null ? null : Calendar.fromId(calendar);
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar == null ? null : calendar.getId();
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getFraction() {
        return fraction;
    }

    public void setFraction(Double fraction) {
        this.fraction = fraction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}