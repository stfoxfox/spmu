package com.company.spmu.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * справочник порядка формирования цены
 */
@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SPMU_PRICING_PROCEDURE")
@Entity(name = "spmu_PricingProcedure")
public class PricingProcedure extends StandardEntity {
    private static final long serialVersionUID = 5206334240434017181L;

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

    /**
     * описание
     */
    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}