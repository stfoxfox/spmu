package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.annotation.Extends;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@PublishEntityChangedEvents
@Entity(name = "spmu_SpmuUser")
@Extends(User.class)
public class SpmuUser extends User {
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne
    @JoinColumn(name = "AFFILIATE_ID")
    protected Affilate affiliate;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANDATE_ID")
    protected Mandate mandate;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    protected Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Mandate getMandate() {
        return mandate;
    }

    public void setMandate(Mandate mandate) {
        this.mandate = mandate;
    }

    public Affilate getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(Affilate affiliate) {
        this.affiliate = affiliate;
    }
}