package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Справочник курсов валют, включая прогнозный курс
 */
@PublishEntityChangedEvents
@Table(name = "SPMU_CURRENCY_RATE")
@Entity(name = "spmu_CurrencyRate")
public class CurrencyRate extends StandardEntity {
    @NotNull
    @Column(name = "CODE", nullable = false, length = 3)
    protected String code;

    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(name = "DATE", nullable = false)
    protected Date date;

    @NotNull
    @Column(name = "RATE", nullable = false)
    protected Double rate;

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}