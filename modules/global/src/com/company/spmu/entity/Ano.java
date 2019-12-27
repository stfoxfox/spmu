package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import java.util.UUID;

/**
 * справочник АНО
 */
@PublishEntityChangedEvents
@Table(name = "SPMU_ANO")
@Entity(name = "spmu_Ano")
public class Ano extends StandardEntity {
    /**
     * тип данных
     */
    @Column(name = "DATA_TYPE")
    protected Integer dataType;

    /**
     * значение
     */
    @Column(name = "VAL")
    protected Double val;

    /**
     * период времени
     */
    @Column(name = "TIME_PERIOD")
    protected UUID timePeriod;

    /**
     * филиал
     * @see Affilate
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AFFILIATE_ID")
    protected Affilate affiliate;

    /**
     * тип полёта
     * @see FlyghtType
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLIGHT_TYPE_ID")
    protected FlyghtType flightType;

    @Column(name = "IS_FACT")
    protected Boolean isFact;

    public Boolean getIsFact() {
        return isFact;
    }

    public void setIsFact(Boolean isFact) {
        this.isFact = isFact;
    }

    public FlyghtType getFlightType() {
        return flightType;
    }

    public void setFlightType(FlyghtType flightType) {
        this.flightType = flightType;
    }

    public Affilate getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(Affilate affiliate) {
        this.affiliate = affiliate;
    }

    public UUID getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(UUID timePeriod) {
        this.timePeriod = timePeriod;
    }

    public Double getVal() {
        return val;
    }

    public void setVal(Double val) {
        this.val = val;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}