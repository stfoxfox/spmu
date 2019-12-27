package com.company.spmu.entity;

import com.company.spmu.enumeration.ApplicationType;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import java.util.List;

/**
 * заявка на мероприятие
 * @see Application
 */
@PublishEntityChangedEvents
@Entity(name = "spmu_ApplicationEvent")
public class ApplicationEvent extends Application {
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

    /**
     * код управленческого учёта (КУУ)
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANALYTICA_ID")
    protected Account analytica;

    /**
     * результат мероприятия
     */
    @Column(name = "RESULT_")
    protected String result;

    /**
     * обоснование мероприятия
     */
    @Column(name = "JUSTIFICATION")
    protected String justification;

    /**
     * обоснование стоимости мероприятия
     */
    @Column(name = "COSTING")
    protected String costing;

    /**
     * признак наличия ГГЭ
     */
    @Column(name = "IS_GGE")
    protected Boolean isGge = false;

    /**
     * признак наличия ПСД
     */
    @Column(name = "IS_PSD")
    protected Boolean isPsd = false;

    /**
     * реквизиты ГГЭ
     */
    @Column(name = "GGE")
    protected String gge;

    /**
     * реквизиты ПСД
     */
    @Column(name = "PSD")
    protected String psd;

    /**
     * ответственный куратор в ГД
     * @see ApplicationCurator
     */
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @Composition
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "application")
    protected ApplicationCurator applicationResponsibleCurator;

    /**
     * ответственный куратор в филиале
     * @see ApplicationManager
     */
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @Composition
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "application")
    protected ApplicationManager applicationResponsibleManager;

    /**
     * признак опциона
     */
    @Column(name = "OPTION_")
    protected Boolean option;

    public void setIsGge(Boolean isGge) {
        this.isGge = isGge;
    }

    public Boolean getIsGge() {
        return isGge;
    }

    public Boolean getOption() {
        return option;
    }

    public void setOption(Boolean option) {
        this.option = option;
    }

    public String getGge() {
        return gge;
    }

    public void setGge(String gge) {
        this.gge = gge;
    }

    public Boolean getIsPsd() {
        return isPsd;
    }

    public void setIsPsd(Boolean isPsd) {
        this.isPsd = isPsd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getPsd() {
        return psd;
    }

    public String getCosting() {
        return costing;
    }

    public void setCosting(String costing) {
        this.costing = costing;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }


    public List<ApplicationEditor> getApplicationResponsibleEditors() {
        return this.applicationResponsibleEditors;
    }

    public void setApplicationResponsibleEditors(List<ApplicationEditor> applicationResponsibleEditors) {
        this.applicationResponsibleEditors = applicationResponsibleEditors;
    }

    public ApplicationInitiator getApplicationResponsibleInitiator() {
        return this.applicationResponsibleInitiator;
    }

    public void setApplicationResponsibleInitiator(ApplicationInitiator applicationResponsibleInitiator) {
        this.applicationResponsibleInitiator = applicationResponsibleInitiator;
    }

    public void setApplicationResponsibleCurator(ApplicationCurator curator) {
        this.applicationResponsibleCurator = curator;
    }

    public ApplicationCurator getApplicationResponsibleCurator() {
        return this.applicationResponsibleCurator;
    }

    public void setApplicationResponsibleManager(ApplicationManager manager) {
        this.applicationResponsibleManager = manager;
    }

    public ApplicationManager getApplicationResponsibleManager() {
        return this.applicationResponsibleManager;
    }

    public Account getAnalytica() {
        return analytica;
    }

    public void setAnalytica(Account analytica) {
        this.analytica = analytica;
    }

    public ApplicationEvent() {
        super();
        this.setType(ApplicationType.EVENT);
        this.setGge("");
        this.setPsd("");
        this.setIsGge(false);
        this.setIsPsd(false);
    }

}
