package com.company.spmu.entity;

import com.company.spmu.enumeration.Kind;
import com.haulmont.chile.core.annotations.Composition;
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
import java.util.UUID;

/**
 * статьи/этапы заявки
 */
@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SPMU_APPLICATION_STAGE")
@Entity(name = "spmu_ApplicationStage")
public class ApplicationStage extends StandardEntity {
    /**
     * родительская статья/этап
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    protected ApplicationStage parent;

    /**
     * наименование
     */
    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    /**
     * описание
     */
    @Column(name = "DESCRIPTION")
    protected String description;

    /**
     * порядковый номер
     */
    @Column(name = "ORDER_")
    protected Integer order;

    /**
     * филиал
     * @see Affilate
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AFFILIATE_ID")
    protected Affilate affiliate;

    /**
     *
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "OBJECT_ID")
    protected ApplicationObject object;

    /**
     * тип этапа
     * TODO: возможно нужно изменить
     * @see ApplicationStageType
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_ID")
    protected ApplicationStageType type;

    /**
     * тип плана
     * @see PlanType
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_ID")
    protected PlanType plan;

    /**
     * раздел/подраздел плана этапа мероприятия
     * @see PlanSection
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECTION_ID")
    protected PlanSection section;

    /**
     * стоимость/лимит
     */
    @Column(name = "VALUE_")
    protected Double value = 0.0;

    /**
     * дата начала
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "START_")
    protected Date start;

    /**
     * дата окончания
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "END_")
    protected Date end;

    /**
     * заявка, к которой прикреплёна статья/этап
     * @see Application
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "APPLICATION_ID")
    protected Application application;

    /**
     * задача
     * TODO убрать
     */
    @Column(name = "TASK_")
    protected UUID task;

    /**
     * TODO убрать
     */
    @Column(name = "EDIT_NEEDED")
    protected Boolean editNeeded = false;

    /**
     * признак наличия ГГЭ
     * TODO проверить целесообразность поля
     */
    @Column(name = "IS_GGE")
    protected Boolean isGge = false;

    /**
     * реквизиты ГГЭ
     * TODO проверить целесообразность поля
     */
    @Column(name = "GGE_DET")
    protected String ggeDet = "";

    /**
     * признак наличия ПСД
     * TODO проверить целесообразность поля
     */
    @Column(name = "IS_PSD")
    protected Boolean isPsd = false;

    /**
     * реквизиты ПСД
     * TODO проверить целесообразность поля
     */
    @Column(name = "PSD_DET")
    protected String psdDet = "";

    /**
     * статус
     * TODO проверить целесообразность поля
     */
    @Column(name = "STATUS")
    protected Integer status = 0;

    @Column(name = "Q1")
    protected Double q1 = 0.0;

    @Column(name = "Q2")
    protected Double q2 = 0.0;

    @Column(name = "Q3")
    protected Double q3 = 0.0;

    @Column(name = "Q4")
    protected Double q4 = 0.0;

    @Column(name = "M1")
    protected Double m1 = 0.0;

    @Column(name = "M2")
    protected Double m2 = 0.0;

    @Column(name = "M3")
    protected Double m3 = 0.0;

    @Column(name = "M4")
    protected Double m4 = 0.0;

    @Column(name = "M5")
    protected Double m5 = 0.0;

    @Column(name = "M6")
    protected Double m6 = 0.0;

    @Column(name = "M7")
    protected Double m7 = 0.0;

    @Column(name = "M8")
    protected Double m8 = 0.0;

    @Column(name = "M9")
    protected Double m9 = 0.0;

    @Column(name = "M10")
    protected Double m10 = 0.0;

    @Column(name = "M11")
    protected Double m11 = 0.0;

    @Column(name = "M12")
    protected Double m12 = 0.0;

    /**
     * признак выполнения этапа
     */
    @NotNull
    @Column(name = "CLOSED_")
    private Boolean closed = false;

    /**
     * статья ВПО
     * @see PlanArticle
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_ARTICLE_ID")
    protected PlanArticle planArticle;

    /**
     * суммы по этапу/кварталу
     * TODO сделать OneToMany или убрать
     */
    @Composition
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "applicationStage")
    protected ApplicationStageValue applicationStageValue;

    /**
     * договор
     * @see ApplicationStageContract
     */
    @Composition
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "stage")
    protected ApplicationStageContract applicationStageContract;

    /**
     * код управленческого учёта (КУУ)
     * @see Account
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANALYTICA_ID")
    protected Account analytica;

    /**
     * Вид расхода/дохода
     * @see Kind
     */
    @Column(name = "KIND")
    protected String kind;

    /**
     * признак отображения лимитов по кварталам/месяцам
     * TODO переименовать по codestyle
     */
    @NotNull
    @Column(nullable = false, name = "IS_QUARTAL")
    protected Boolean is_quartal = false;

    /**
     * ответственный в ГД
     * @see ApplicationStageCurator
     */
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @Composition
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "applicationStage")
    protected ApplicationStageCurator applicationStageResponsibleCurator;

    /**
     * ответственный в филиале
     * @see ApplicationManager
     */
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @Composition
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "applicationStage")
    protected ApplicationStageManager applicationStageResponsibleManager;

    /**
     * основые средства
     * @see FixedAsset
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIXED_ASSET_ID")
    protected FixedAsset fixedAsset;

    /**
     * место размещения
     * @see Location
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    protected Location location;

    /**
     * оборудование/объекты
     * @see EquipmentObject
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EQUIPMENT_OBJECT_ID")
    protected EquipmentObject equipmentObject;

    /**
     * приоритет
     * @see Priority
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRIORITY_ID")
    protected Priority priority;

    /**
     *
     * @see PricingProcedure
     */
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRICING_PROCEDURE_ID")
    protected PricingProcedure pricingProcedure;

    public ApplicationStageContract getApplicationStageContract() {
        return applicationStageContract;
    }

    public void setApplicationStageContract(ApplicationStageContract applicationStageContract) {
        this.applicationStageContract = applicationStageContract;
    }

    public ApplicationStageValue getApplicationStageValue() {
        return applicationStageValue;
    }

    public void setApplicationStageValue(ApplicationStageValue applicationStageValue) {
        this.applicationStageValue = applicationStageValue;
    }

    public PlanArticle getPlanArticle() {
        return planArticle;
    }

    public void setPlanArticle(PlanArticle planArticle) {
        this.planArticle = planArticle;
    }

    public void setParent(ApplicationStage parent) {
        this.parent = parent;
    }

    public ApplicationStage getParent() {
        return parent;
    }

    public void setObject(ApplicationObject object) {
        this.object = object;
    }

    public ApplicationObject getObject() {
        return object;
    }

    public void setSection(PlanSection section) {
        this.section = section;
    }

    public PlanSection getSection() {
        return section;
    }

    public void setType(ApplicationStageType type) {
        this.type = type;
    }

    public ApplicationStageType getType() {
        return type;
    }

    public Double getM12() {
        return m12;
    }

    public void setM12(Double m12) {
        this.m12 = m12;
    }

    public Double getM11() {
        return m11;
    }

    public void setM11(Double m11) {
        this.m11 = m11;
    }

    public Double getM10() {
        return m10;
    }

    public void setM10(Double m10) {
        this.m10 = m10;
    }

    public Double getM9() {
        return m9;
    }

    public void setM9(Double m9) {
        this.m9 = m9;
    }

    public Double getM8() {
        return m8;
    }

    public void setM8(Double m8) {
        this.m8 = m8;
    }

    public Double getM7() {
        return m7;
    }

    public void setM7(Double m7) {
        this.m7 = m7;
    }

    public Double getM6() {
        return m6;
    }

    public void setM6(Double m6) {
        this.m6 = m6;
    }

    public Double getM5() {
        return m5;
    }

    public void setM5(Double m5) {
        this.m5 = m5;
    }

    public Double getM4() {
        return m4;
    }

    public void setM4(Double m4) {
        this.m4 = m4;
    }

    public Double getM3() {
        return m3;
    }

    public void setM3(Double m3) {
        this.m3 = m3;
    }

    public Double getM2() {
        return m2;
    }

    public void setM2(Double m2) {
        this.m2 = m2;
    }

    public Double getM1() {
        return m1;
    }

    public void setM1(Double m1) {
        this.m1 = m1;
    }

    public Double getQ4() {
        return q4;
    }

    public void setQ4(Double q4) {
        this.q4 = q4;
    }

    public Double getQ3() {
        return q3;
    }

    public void setQ3(Double q3) {
        this.q3 = q3;
    }

    public Double getQ2() {
        return q2;
    }

    public void setQ2(Double q2) {
        this.q2 = q2;
    }

    public Double getQ1() {
        return q1;
    }

    public void setQ1(Double q1) {
        this.q1 = q1;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPsdDet() {
        return psdDet;
    }

    public void setPsdDet(String psdDet) {
        this.psdDet = psdDet;
    }

    public Boolean getIsPsd() {
        return isPsd;
    }

    public void setIsPsd(Boolean isPsd) {
        this.isPsd = isPsd;
    }

    public String getGgeDet() {
        return ggeDet;
    }

    public void setGgeDet(String ggeDet) {
        this.ggeDet = ggeDet;
    }

    public Boolean getIsGge() {
        return isGge;
    }

    public void setIsGge(Boolean isGge) {
        this.isGge = isGge;
    }

    public Boolean getEditNeeded() {
        return editNeeded;
    }

    public void setEditNeeded(Boolean editNeeded) {
        this.editNeeded = editNeeded;
    }

    public UUID getTask() {
        return task;
    }

    public void setTask(UUID task) {
        this.task = task;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public PlanType getPlan() {
        return plan;
    }

    public void setPlan(PlanType plan) {
        this.plan = plan;
    }

    public Affilate getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(Affilate affiliate) {
        this.affiliate = affiliate;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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

    public ApplicationStage getClone() {
        try {
            ApplicationStage result = (ApplicationStage) this.clone();
            return result;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Account getAnalytica() {
        return analytica;
    }

    public void setAnalytica(Account analytica) {
        this.analytica = analytica;
    }

    public void setKind(Kind kind) {
        this.kind = kind == null ? null : kind.getId();
    }

    public Kind getKind() {
        return kind == null ? null : Kind.fromId(kind);
    }

    public Boolean getIs_quartal() {
        return is_quartal;
    }

    public void setIs_quartal(Boolean is_quartal) {
        this.is_quartal = is_quartal;
    }

    public ApplicationStageCurator getApplicationStageResponsibleCurator() {
        return applicationStageResponsibleCurator;
    }

    public void setApplicationStageResponsibleCurator(ApplicationStageCurator applicationStageResponsibleCurator) {
        this.applicationStageResponsibleCurator = applicationStageResponsibleCurator;
    }

    public ApplicationStageManager getApplicationStageResponsibleManager() {
        return applicationStageResponsibleManager;
    }

    public void setApplicationStageResponsibleManager(ApplicationStageManager applicationStageResponsibleManager) {
        this.applicationStageResponsibleManager = applicationStageResponsibleManager;
    }

    public FixedAsset getFixedAsset() {
        return fixedAsset;
    }

    public void setFixedAsset(FixedAsset fixedAsset) {
        this.fixedAsset = fixedAsset;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public EquipmentObject getEquipmentObject() {
        return equipmentObject;
    }

    public void setEquipmentObject(EquipmentObject equipmentObject) {
        this.equipmentObject = equipmentObject;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setPricingProcedure(PricingProcedure pricingProcedure) {
        this.pricingProcedure = pricingProcedure;
    }

    public PricingProcedure getPricingProcedure() {
        return pricingProcedure;
    }

    public void setCode(int i) {
    }
}
