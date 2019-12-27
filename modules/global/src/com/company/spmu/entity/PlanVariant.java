package com.company.spmu.entity;

import com.company.spmu.enumeration.PlanVariantStatus;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * вариант плана
 */
@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SPMU_PLAN_VARIANT")
@Entity(name = "spmu_PlanVariant")
public class PlanVariant extends StandardEntity {

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

    /**
     * год планирования
     */
    @Column(name = "YEAR")
    protected Integer year;

    /**
     * Дата актуальности данных
     */
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    protected Date date;

    @MetaProperty
    @Composition
    @OneToMany(mappedBy = "planVariant")
    @OrderBy("year ASC")
    protected List<PlanVariantLimit> limits;

    /**
     * ответственный сотрудник
     * @see Employee
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESPONSIBLE_ID")
    protected Employee responsible;

    /**
     * Статус варианта плана
     * @see PlanVariantStatus
     */
    @Column(name = "STATUS")
    protected String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Employee getResponsible() {
        return responsible;
    }

    public void setResponsible(Employee responsible) {
        this.responsible = responsible;
    }

    public PlanVariantStatus getStatus() {
        return status == null ? null : PlanVariantStatus.fromId(status);
    }

    public void setStatus(PlanVariantStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public List<PlanVariantLimit> getLimits() {
        return limits;
    }

    public void setLimits(List<PlanVariantLimit> limits) {
        this.limits = limits;
    }

}
