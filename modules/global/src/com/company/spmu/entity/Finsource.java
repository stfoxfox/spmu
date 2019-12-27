package com.company.spmu.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Справочник источников финансирования (бюджет, собственные средства и т.п.)
 */
@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SPMU_FINSOURCE")
@Entity(name = "spmu_Finsource")
public class Finsource extends StandardEntity {
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
}