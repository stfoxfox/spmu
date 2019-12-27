package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@PublishEntityChangedEvents
@Table(name = "SPMU_APPLICATION_STAGE_CONTRACT")
@Entity(name = "spmu_ApplicationStageContract")
public class ApplicationStageContract extends StandardEntity {
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STAGE_ID")
    protected ApplicationStage stage;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACT_ID")
    protected ContractStage contract;

    @Column(name = "AMOUNT")
    protected Double amount;

    @Column(name = "ACCRUED")
    protected Double accrued;

    @Column(name = "PAID")
    protected Double paid;

    @Column(name = "COMPENSATED")
    protected Double compensated;

    public Double getCompensated() {
        return compensated;
    }

    public void setCompensated(Double compensated) {
        this.compensated = compensated;
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public Double getAccrued() {
        return accrued;
    }

    public void setAccrued(Double accrued) {
        this.accrued = accrued;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ContractStage getContract() {
        return contract;
    }

    public void setContract(ContractStage contract) {
        this.contract = contract;
    }

    public ApplicationStage getStage() {
        return stage;
    }

    public void setStage(ApplicationStage stage) {
        this.stage = stage;
    }
}