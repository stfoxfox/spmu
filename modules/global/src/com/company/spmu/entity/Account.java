package com.company.spmu.entity;

import com.company.spmu.entity.interfaces.HasGuid;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Справочник кодов управленческого учета
 */
@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SPMU_ACCOUNT")
@Entity(name = "spmu_Account")
public class Account extends StandardEntity implements HasGuid {
    /**
     * guid
     */
    @Column(name = "GUID")
    protected UUID guid;

    /**
     * код
     */
    @Column(name = "CODE", length = 32)
    protected String code;

    /**
     * наименование
     */
    @Column(name = "NAME")
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
