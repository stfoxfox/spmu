package com.company.spmu.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import java.util.Date;

@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SPMU_CONTRACT_STAGE")
@Entity(name = "spmu_ContractStage")
public class ContractStage extends StandardEntity {
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACT_ID")
    protected Contract contract;

    @Column(name = "NUM")
    protected Integer num;

    @Column(name = "CODE", length = 32)
    protected String code;

    @Column(name = "NAME")
    protected String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_")
    protected Date start;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_")
    protected Date end;

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}