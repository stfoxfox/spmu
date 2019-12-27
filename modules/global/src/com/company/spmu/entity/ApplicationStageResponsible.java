package com.company.spmu.entity;

import com.company.spmu.enumeration.ApplicationResponsibleRole;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import javax.persistence.*;

@NamePattern("%s|employee")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "SPMU_APPLICATION_STAGE_RESPONSIBLE")
@Entity(name = "spmu_ApplicationStageResponsible")
@DiscriminatorColumn(name="ROLE", discriminatorType=DiscriminatorType.STRING)
public abstract class ApplicationStageResponsible extends StandardEntity {
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATIONSTAGE_ID")
    protected ApplicationStage applicationStage;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID")
    protected Employee employee;

    @Column(name = "ROLE")
    protected String role;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ApplicationStage getApplicationStage() {
        return applicationStage;
    }

    public void setApplicationStage(ApplicationStage applicationStage) {
        this.applicationStage = applicationStage;
    }

    public ApplicationResponsibleRole getRole() {
        return role == null ? null : ApplicationResponsibleRole.fromId(role);
    }

    public void setRole(ApplicationResponsibleRole role) {
        this.role = role == null ? null : role.getId();
    }

}
