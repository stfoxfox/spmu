package com.company.spmu.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SPMU_POSITION")
@Entity(name = "spmu_Position")
public class Position extends StandardEntity {
    private static final long serialVersionUID = -8231367502848248249L;

    @Column(name="CODE", length = 32)
    protected String code;

    @Column(name="NAME")
    protected String name;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
