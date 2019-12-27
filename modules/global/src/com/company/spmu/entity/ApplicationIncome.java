package com.company.spmu.entity;

import com.company.spmu.enumeration.ApplicationType;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;
import com.haulmont.cuba.core.entity.annotation.TrackEditScreenHistory;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import java.util.List;

/**
 * заявка на доходы
 * @see Application
 */
@PublishEntityChangedEvents
@TrackEditScreenHistory
@Entity(name = "spmu_ApplicationIncome")
public class ApplicationIncome extends Application {
    /**
     * редактора заявки
     * @see ApplicationEditor
     */
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "application")
    protected List<ApplicationEditor> applicationResponsibleEditors;

    /**
     * инициатор заявки
     * @see ApplicationInitiator
     */
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @Composition
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "application")
    protected ApplicationInitiator applicationResponsibleInitiator;

    public List<ApplicationEditor> getApplicationResponsibleEditors() {
        return this.applicationResponsibleEditors;
    }

    public void setApplicationResponsibleEditors(List<ApplicationEditor> applicationResponsibleEditors) {
        this.applicationResponsibleEditors = applicationResponsibleEditors;
    }

    @MetaProperty
    @Transient
    protected ApplicationIncome application;

    public void setApplication(ApplicationIncome application) {
        this.application = application;
    }

    public ApplicationIncome getApplication() {
        return application;
    }

    public ApplicationInitiator getApplicationResponsibleInitiator() {
        return this.applicationResponsibleInitiator;
    }

    public void setApplicationResponsibleInitiator(ApplicationInitiator applicationResponsibleInitiator) {
        this.applicationResponsibleInitiator = applicationResponsibleInitiator;
    }

    public ApplicationIncome() {
        super();
        this.setType(ApplicationType.INCOME);
    }

}
