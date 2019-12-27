package com.company.spmu.entity;

import com.company.spmu.enumeration.ApplicationActive;
import com.company.spmu.enumeration.ApplicationStatus;
import com.company.spmu.enumeration.ApplicationType;
import com.company.spmu.enumeration.Kind;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Заявки
 */
@NamePattern("%s|name")
@Table(name = "SPMU_APPLICATION")
@Entity(name = "spmu_Application")
public class Application extends StandardEntity {
    /**
     * тип заявки
     *
     * @see ApplicationType
     */
    @NotNull
    @Column(name = "TYPE_", nullable = false)
    protected String type;

    /**
     * вид расходов/доходов
     *
     * @see Kind
     */
    @Column(name = "KIND")
    protected String kind;

    /**
     * признак корректировки
     */
    @NotNull
    @Column(name = "CHANGE", nullable = false)
    protected Boolean change = false;

    /**
     * вид заявки
     *
     * @see ApplicationActive
     */
    @NotNull
    @Column(name = "ACTIVE", nullable = false)
    protected String active;

    /**
     * год (плановый или инициализации, зависит от заявки)
     */
    @NotNull
    @Column(name = "YEAR", nullable = false)
    protected Integer year;

    /**
     * код
     */
    @Column(name = "CODE")
    protected String code;

    /**
     * дата заявки
     */
    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(name = "DATE", nullable = false)
    protected Date date;

    /**
     * филиал
     *
     * @see Affilate
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AFFILIATE_ID")
    protected Affilate affiliate;

    /**
     * дата начала
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_START")
    protected Date dateStart;

    /**
     * дата окончания
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_END")
    protected Date dateEnd;

    /**
     * ответственный сотрудник
     *
     * @see Employee
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESPONSIBLE_ID")
    protected Employee responsible;

    /**
     * цель мероприятия
     *
     * @see Target
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TARGET_ID")
    protected Target target;

    /**
     * общая стоимость
     */
    @NotNull
    @Column(name = "VALUE", nullable = false)
    protected Double value;

    /**
     * наименование
     */
    @Column(name = "NAME")
    protected String name;

    /**
     * описание (комментарий)
     */
    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    /**
     * тип плана
     *
     * @see PlanType
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_ID")
    protected PlanType plan;

    /**
     * статья ВПО
     * TODO выпилить
     *
     * @see PlanArticle
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTICLE_ID")
    protected PlanArticle article;

    /**
     * порядок мероприятия
     */
    @Column(name = "ORDER_")
    protected Integer order;

    /**
     * тут что то непонятное
     */
    @Column(name = "LAYOUT")
    protected Integer layout;

    /**
     * назначение
     */
    @Column(name = "ASSIGNMENT")
    protected String assignment;

    /**
     * Доказательство
     */
    @Column(name = "PROOF")
    protected String proof;

    /**
     * Статус заявки
     *
     * @see ApplicationStatus
     */
    @Column(name = "STATUS")
    protected String status;

    /**
     * подразделение
     *
     * @see Division
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIVISION_ID")
    protected Division division;

    /**
     * статья ВПО
     *
     * @see PlanArticle
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_ARTICLE_ID")
    protected PlanArticle planArticle;

    @MetaProperty
    @Composition
    @OneToMany(mappedBy = "application")
    protected List<PlanVariantTemplateApplication> planVariantTemplateApplications;

    public PlanArticle getPlanArticle() {
        return planArticle;
    }

    public void setPlanArticle(PlanArticle planArticle) {
        this.planArticle = planArticle;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public ApplicationStatus getStatus() {
        return status == null ? null : ApplicationStatus.fromId(status);
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public void setType(ApplicationType type) {
        this.type = type == null ? null : type.getId();
    }

    public ApplicationType getType() {
        return type == null ? null : ApplicationType.fromId(type);
    }

    public void setKind(Kind kind) {
        this.kind = kind == null ? null : kind.getId();
    }

    public Kind getKind() {
        return kind == null ? null : Kind.fromId(kind);
    }

    public void setActive(ApplicationActive active) {
        this.active = active == null ? null : active.getId();
    }

    public ApplicationActive getActive() {
        return active == null ? null : ApplicationActive.fromId(active);
    }

    public Employee getResponsible() {
        return responsible;
    }

    public void setResponsible(Employee responsible) {
        this.responsible = responsible;
    }

    public void setPlan(PlanType plan) {
        this.plan = plan;
    }

    public PlanType getPlan() {
        return plan;
    }

    public void setChange(Boolean change) {
        this.change = change;
    }

    public Boolean getChange() {
        return change;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public Integer getLayout() {
        return layout;
    }

    public void setLayout(Integer layout) {
        this.layout = layout;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public PlanArticle getArticle() {
        return article;
    }

    public void setArticle(PlanArticle article) {
        this.article = article;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Affilate getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(Affilate affiliate) {
        this.affiliate = affiliate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Application() {
        super();
        this.setValue(0.0);
        this.setStatus(ApplicationStatus.PROJECT);
        this.setActive(ApplicationActive.ACTIVE);
    }

    public List<PlanVariantTemplateApplication> getPlanVariantTemplateApplications() {
        return planVariantTemplateApplications;
    }

    public void setPlanVariantTemplateApplications(List<PlanVariantTemplateApplication> planVariantTemplateApplications) {
        this.planVariantTemplateApplications = planVariantTemplateApplications;
    }

}
