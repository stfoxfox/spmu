package com.company.spmu.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * справочник приоритетов
 */
@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SPMU_PRIORITY")
@Entity(name = "spmu_Priority")
public class Priority extends StandardEntity {
    private static final long serialVersionUID = 4002681658143966262L;

    /**
     * код
     */
    @Column(name = "CODE")
    protected String code;

    /**
     * наименование
     */
    @Column(name = "NAME")
    protected String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
}