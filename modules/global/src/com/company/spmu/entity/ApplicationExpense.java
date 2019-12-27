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
 * заявка на расходы
 * @see Application
 */
@PublishEntityChangedEvents
@TrackEditScreenHistory
@Entity(name = "spmu_ApplicationExpense")
public class ApplicationExpense extends Application {

    /**
     * редактора заявки
     * @see ApplicationEditor
     */
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "application")
    protected List<ApplicationEditor> applicationResponsibleEditors;

    /**
     * инициаторы заявки
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
    protected ApplicationExpense application;

    public void setApplication(ApplicationExpense application) {
        this.application = application;
    }

    public ApplicationExpense getApplication() {
        return application;
    }

    public ApplicationInitiator getApplicationResponsibleInitiator() {
        return this.applicationResponsibleInitiator;
    }

    public void setApplicationResponsibleInitiator(ApplicationInitiator applicationResponsibleInitiator) {
        this.applicationResponsibleInitiator = applicationResponsibleInitiator;
    }

    public ApplicationExpense() {
        super();
        this.setType(ApplicationType.EXPENSE);
    }

}
