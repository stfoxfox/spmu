package com.company.spmu.entity;

import com.company.spmu.enumeration.ApplicationStatus;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@PublishEntityChangedEvents
@Table(name = "SPMU_STATUS_LINK")
@Entity(name = "spmu_StatusLink")
public class StatusLink extends StandardEntity {
    private static final long serialVersionUID = 2654044956839436034L;

    @Column(name = "STATUS")
    protected String status;

    @Column(name = "CODE")
    protected String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public ApplicationStatus getStatus() {
        return status == null ? null : ApplicationStatus.fromId(status);
    }
}