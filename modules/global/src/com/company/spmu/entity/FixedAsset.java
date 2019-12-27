package com.company.spmu.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import java.util.Date;

/**
 * Основные средства
 */
@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SPMU_FIXED_ASSET")
@Entity(name = "spmu_FixedAsset")
public class FixedAsset extends StandardEntity {
    private static final long serialVersionUID = -7062961989589118727L;

    /**
     * ссылка на родителя
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    protected FixedAsset parent;

    /**
     * наименование
     */
    @Column(name = "NAME")
    protected String name;

    /**
     * код
     */
    @Column(name = "CODE")
    protected String code;

    /**
     * полное наименование
     */
    @Lob
    @Column(name = "FULL_NAME")
    protected String fullName;

    /**
     * изготовитель
     */
    @Column(name = "VENDOR")
    protected String vendor;

    /**
     * заводской номер
     */
    @Column(name = "FACTORY_NUMBER")
    protected String factoryNumber;

    /**
     * номер паспорта
     */
    @Column(name = "PASSPORT_NUMBER")
    protected String passportNumber;

    /**
     * дата выпуска
     */
    @Column(name = "RELEASED_AT")
    protected Date releasedAt;

    public FixedAsset getParent() {
        return parent;
    }

    public void setParent(FixedAsset parent) {
        this.parent = parent;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Date getReleasedAt() {
        return releasedAt;
    }

    public void setReleasedAt(Date releasedAt) {
        this.releasedAt = releasedAt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFactoryNumber() {
        return factoryNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getVendor() {
        return vendor;
    }

    public void setFactoryNumber(String factoryNumber) {
        this.factoryNumber = factoryNumber;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }
}