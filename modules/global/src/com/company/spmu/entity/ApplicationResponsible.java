package com.company.spmu.entity;

import com.company.spmu.enumeration.ApplicationResponsibleRole;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
@PublishEntityChangedEvents
@NamePattern("%s|employee")
@Table(name = "SPMU_APPLICATION_RESPONSIBLE")
@Entity(name = "spmu_ApplicationResponsible")
@MappedSuperclass
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="ROLE", discriminatorType = DiscriminatorType.STRING)
public abstract class ApplicationResponsible extends StandardEntity {
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_ID")
    protected Application application;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID")
    protected Employee employee;

    @Column(name = "ROLE")
    protected String role;

    public ApplicationResponsibleRole getRole() {
        return role == null ? null : ApplicationResponsibleRole.fromId(role);
    }

    public void setRole(ApplicationResponsibleRole role) {
        this.role = role == null ? null : role.getId();
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
