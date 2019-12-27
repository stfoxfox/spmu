package com.company.spmu.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;

/**
 * справочник оборудования/объектов
 */
@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SPMU_EQUIPMENT_OBJECT")
@Entity(name = "spmu_EquipmentObject")
public class EquipmentObject extends StandardEntity {
    private static final long serialVersionUID = 5560063145558890805L;

    /**
     * родитель
     * @see EquipmentObject
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    protected EquipmentObject parent;

    /**
     * наименование
     */
    @Column(name = "NAME")
    protected String name;

    /**
     * код
     */
    @Column(name = "code")
    protected String code;

    /**
     * признак оборудования/объекта
     */
    @Column(name = "IS_EQUIPMENT")
    protected Boolean isEquipment;

    /**
     * координаты
     */
    @Column(name = "GEO_COORDINATE")
    protected String geoCoordinate;

    /**
     * филиал
     * @see Affilate
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AFFILATE_ID")
    protected Affilate affilate;

    /**
     * подразделение
     * @see Division
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DIVISION_ID")
    protected Division division;

    /**
     * место размещения
     * @see Location
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LOCATION_ID")
    protected Location location;

    /**
     * признак подобъекта
     */
    @Column(name = "IS_SUBOBJECT")
    protected Boolean isSubobject;

    /**
     * инвентарный номер
     */
    @Column(name = "INVENTORY_NUMBER")
    protected String inventoryNumber;

    /**
     * заводской номер
     */
    @Column(name = "FACTORY_NUMBER")
    protected String factoryNumber;

    /**
     * год выпуска
     */
    @Column(name = "RELEASED_YEAR")
    protected Integer releasedYear;

    public void setParent(EquipmentObject parent) {
        this.parent = parent;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public EquipmentObject getParent() {
        return parent;
    }

    public Boolean getIsEquipment() {
        return isEquipment;
    }

    public void setFactoryNumber(String factoryNumber) {
        this.factoryNumber = factoryNumber;
    }

    public Affilate getAffilate() {
        return affilate;
    }

    public Boolean getIsSubobject() {
        return isSubobject;
    }

    public String getGeoCoordinate() {
        return geoCoordinate;
    }

    public Division getDivision() {
        return division;
    }

    public String getFactoryNumber() {
        return factoryNumber;
    }

    public void setAffilate(Affilate affilate) {
        this.affilate = affilate;
    }

    public void setIsEquipment(Boolean isEquipment) {
        this.isEquipment = isEquipment;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public void setGeoCoordinate(String geoCoordinate) {
        this.geoCoordinate = geoCoordinate;
    }

    public Integer getReleasedYear() {
        return releasedYear;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public void setReleasedYear(Integer releasedYear) {
        this.releasedYear = releasedYear;
    }

    public void setIsSubobject(Boolean isSubobject) {
        this.isSubobject = isSubobject;
    }

}