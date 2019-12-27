package com.company.spmu.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import java.util.UUID;

/**
 *
 */
@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SPMU_APPLICATION_OBJECT")
@Entity(name = "spmu_ApplicationObject")
public class ApplicationObject extends StandardEntity {
    @Column(name = "GUID")
    protected UUID guid;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORIGIN_ID")
    protected ApplicationObject origin;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    protected ApplicationObject parent;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AFFILIATE_ID")
    protected Affilate affiliate;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIVISION_ID")
    protected Division division;

    @Column(name = "RTOPUID")
    protected UUID rtopuid;

    @Column(name = "LOCATION")
    protected String location;

    @Column(name = "LOCATION_ID")
    protected Integer locationId;

    @Column(name = "COORDINATE")
    protected String coordinate;

    @Column(name = "NAME")
    protected String name;

    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_ID")
    protected ApplicationObjectType type;

    @Column(name = "LINENUM")
    protected Integer linenum;

    @Column(name = "QUANTITY")
    protected Integer quantity;

    public void setType(ApplicationObjectType type) {
        this.type = type;
    }

    public ApplicationObjectType getType() {
        return type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getLinenum() {
        return linenum;
    }

    public void setLinenum(Integer linenum) {
        this.linenum = linenum;
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

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UUID getRtopuid() {
        return rtopuid;
    }

    public void setRtopuid(UUID rtopuid) {
        this.rtopuid = rtopuid;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Affilate getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(Affilate affiliate) {
        this.affiliate = affiliate;
    }

    public ApplicationObject getParent() {
        return parent;
    }

    public void setParent(ApplicationObject parent) {
        this.parent = parent;
    }

    public ApplicationObject getOrigin() {
        return origin;
    }

    public void setOrigin(ApplicationObject origin) {
        this.origin = origin;
    }

    public UUID getGuid() {
        return guid;
    }

    public void setGuid(UUID guid) {
        this.guid = guid;
    }
}