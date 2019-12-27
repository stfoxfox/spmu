package com.company.spmu.entity;

import com.company.spmu.entity.interfaces.HasGuid;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Справочник структурных подразделений (филиалов)
 */
@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SPMU_AFFILATE")
@Entity(name = "spmu_Affilate")
public class Affilate extends StandardEntity implements HasGuid {
    /**
     * guid
     */
    @NotNull
    @Column(name = "GUID", nullable = false)
    protected UUID guid;

    /**
     * ссылка на родителя
     * @see Affilate
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    protected Affilate parent;

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

    /**
     * описание
     */
    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    /**
     * полное наименование
     */
    @Column(name = "FULLNAME")
    protected String fullname;

    public void setParent(Affilate parent) {
        this.parent = parent;
    }

    public Affilate getParent() {
        return parent;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    @Override
    public UUID getGuid() {
        return guid;
    }

    @Override
    public void setGuid(UUID guid) {
        this.guid = guid;
    }
}