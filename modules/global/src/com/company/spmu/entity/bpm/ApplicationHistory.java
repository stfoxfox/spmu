package com.company.spmu.entity.bpm;

import com.company.spmu.entity.SpmuUser;
import com.haulmont.bpm.entity.ProcTask;
import com.haulmont.cuba.core.entity.EntitySnapshot;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "SPMU_APPLICATION_HISTORY")
@Entity(name = "spmu_ApplicationHistory")
public class ApplicationHistory extends StandardEntity {
    private static final long serialVersionUID = -2493210833320229966L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(name = "ENTITY_SNAPSHOT_ID")
    protected EntitySnapshot entitySnapshot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROC_TASK_ID")
    protected ProcTask procTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    protected SpmuUser user;

    public SpmuUser getUser() {
        return user;
    }

    public void setUser(SpmuUser user) {
        this.user = user;
    }

    public ProcTask getProcTask() {
        return procTask;
    }

    public void setProcTask(ProcTask procTask) {
        this.procTask = procTask;
    }

    public EntitySnapshot getEntitySnapshot() {
        return entitySnapshot;
    }

    public void setEntitySnapshot(EntitySnapshot entitySnapshot) {
        this.entitySnapshot = entitySnapshot;
    }
}