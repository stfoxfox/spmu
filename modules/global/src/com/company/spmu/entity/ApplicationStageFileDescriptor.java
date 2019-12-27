package com.company.spmu.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@PublishEntityChangedEvents
@NamePattern(value = "%s|file.name")
@Table(name = "SPMU_APPLICATION_STAGE_FILE_DESCRIPTOR")
@Entity(name = "spmu_ApplicationStageFileDescriptor")
public class ApplicationStageFileDescriptor extends StandardEntity {
    private static final long serialVersionUID = 8981883671082292050L;

    @NotNull
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_ID")
    protected ApplicationStage application;

    @NotNull
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID")
    protected FileDescriptor file;

    @NotNull
    @Column(name = "DESCRIPTION_")
    protected String description = "";

    public ApplicationStage getApplication() {
        return application;
    }

    public void setApplication(ApplicationStage application) {
        this.application = application;
    }

    public FileDescriptor getFile() {
        return file;
    }

    public void setFile(FileDescriptor file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
