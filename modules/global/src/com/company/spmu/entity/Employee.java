package com.company.spmu.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@PublishEntityChangedEvents
@NamePattern("%s %s|firstname,lastname")
@Table(name = "SPMU_EMPLOYEE")
@Entity(name = "spmu_Employee")
public class Employee extends StandardEntity {
    @NotNull
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MANDATE_ID")
    protected Mandate mandate;

    @NotNull
    @Column(name = "FIRSTNAME", nullable = false)
    protected String firstname;

    @NotNull
    @Column(name = "SECONDNAME", nullable = false)
    protected String secondname;

    @NotNull
    @Column(name = "LASTNAME", nullable = false)
    protected String lastname;

    @Column(name = "GENDER")
    protected String gender;

    @Temporal(TemporalType.DATE)
    @Column(name = "BITHDATE")
    protected Date bithdate;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POSITION_ID")
    protected Position position;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "employee")
    protected List<ApplicationResponsible> applicationResponsibles;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "employee")
    protected List<ApplicationStageResponsible> applicationStageResponsibles;

    public List<ApplicationResponsible> getApplicationResponsibles() {
        return applicationResponsibles;
    }

    public void setApplicationResponsibles(List<ApplicationResponsible> applicationResponsibles) {
        this.applicationResponsibles = applicationResponsibles;
    }

    public void setMandate(Mandate mandate) {
        this.mandate = mandate;
    }

    public Mandate getMandate() {
        return mandate;
    }

    public Date getBithdate() {
        return bithdate;
    }

    public void setBithdate(Date bithdate) {
        this.bithdate = bithdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Application getApplication(){
        return null;
    }

    public void setApplication(Application application){

    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<ApplicationStageResponsible> getApplicationStageResponsibles() {
        return applicationStageResponsibles;
    }

    public void setApplicationStageResponsibles(List<ApplicationStageResponsible> applicationStageResponsibles) {
        this.applicationStageResponsibles = applicationStageResponsibles;
    }

}
