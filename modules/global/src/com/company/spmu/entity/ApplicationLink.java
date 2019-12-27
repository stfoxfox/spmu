package com.company.spmu.entity;

import com.company.spmu.enumeration.LinkType;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * взаимосвязи заявки
 */
@PublishEntityChangedEvents
@Table(name = "SPMU_APPLICATION_LINK")
@Entity(name = "spmu_ApplicationLink")
public class ApplicationLink extends StandardEntity {
    private static final long serialVersionUID = -5924993221665887944L;

    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    protected Application parent;

    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_STAGE_ID")
    @NotNull
    protected ApplicationStage parentStage;

    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHILD_STAGE_ID")
    @NotNull
    protected ApplicationStage childStage;

    @Column(name = "LINKTYPE")
    protected String linkType;

    @Column(name = "STEP")
    protected Integer step = 0;

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public ApplicationStage getParentStage() {
        return parentStage;
    }

    public void setParentStage(ApplicationStage parentStage) {
        this.parentStage = parentStage;
    }

    public long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Application getParent() {
        return parent;
    }

    public void setParent(Application parent) {
        this.parent = parent;
    }

    public ApplicationStage getChildStage() {
        return childStage;
    }

    public void setChildStage(ApplicationStage childStage) {
        this.childStage = childStage;
    }

    public LinkType getLinkType() {
        return linkType == null ? null : LinkType.fromId(linkType);
    }

    public void setLinkType(LinkType linkType) {
        this.linkType = linkType == null ? null : linkType.getId();
    }

}