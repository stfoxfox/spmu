package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * дополнительные аттрибуты заявки
 * используется для ApplicationEvent
 * @see ApplicationEvent
 */
@PublishEntityChangedEvents
@Table(name = "SPMU_APPLICATION_DETAIL_1")
@Entity(name = "spmu_ApplicationDetail_1")
public class ApplicationDetail extends StandardEntity {
    /**
     * заявка
     * @see Application
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "APPLICATION_ID")
    protected Application application;

    /**
     * тип дополнительного аттрибута
     * @see ApplicationDetailType
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPE_ID")
    protected ApplicationDetailType type;

    /**
     * значение 1
     */
    @Column(name = "VALUE1")
    protected String value1;

    /**
     * значение 2
     */
    @Column(name = "VALUE2")
    protected String value2;

    /**
     * значение 3
     */
    @Column(name = "VALUE3")
    protected String value3;

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public ApplicationDetailType getType() {
        return type;
    }

    public void setType(ApplicationDetailType type) {
        this.type = type;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}