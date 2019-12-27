package com.company.spmu.entity;

import com.company.spmu.entity.interfaces.HasGuid;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Справочник юридических лиц
 */
@PublishEntityChangedEvents
@Table(name = "SPMU_ORGANIZATION")
@Entity(name = "spmu_Organization")
public class Organization extends StandardEntity implements HasGuid {
    @NotNull
    @Column(name = "GUID", nullable = false)
    protected UUID guid;

    @NotNull
    @Column(name = "CODE", nullable = false, length = 32)
    protected String code;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

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