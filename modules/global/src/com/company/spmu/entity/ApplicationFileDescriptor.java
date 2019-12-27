package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * приложения к заявке
 */
@PublishEntityChangedEvents
@Table(name = "SPMU_APPLICATION_FILE_DESCRIPTOR")
@Entity(name = "spmu_ApplicationFileDescriptor")
public class ApplicationFileDescriptor extends StandardEntity {
    private static final long serialVersionUID = -6819321065317370211L;


    /**
     * заявка
     * @see Application
     */
    @NotNull
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_ID")
    protected Application application;

    /**
     * файл
     * @see FileDescriptor
     */
    @NotNull
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID")
    protected FileDescriptor file;

    /**
     * описание
     */
    @NotNull
    @Column(name = "DESCRIPTION_", length = 255)
    protected String description = "";

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
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
