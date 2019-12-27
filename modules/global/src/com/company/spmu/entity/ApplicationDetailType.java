package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * типы дополнительных аттрибутов
 */
@PublishEntityChangedEvents
@Table(name = "SPMU_APPLICATION_DETAIL")
@Entity(name = "spmu_ApplicationDetail")
public class ApplicationDetailType extends StandardEntity {
    /**
     * код
     */
    @NotNull
    @Column(name = "CODE", nullable = false, length = 32)
    protected String code;

    /**
     * наименование
     */
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
