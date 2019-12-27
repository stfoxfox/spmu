package com.company.spmu.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;
import java.util.List;
import javax.persistence.*;

@PublishEntityChangedEvents
@Table(name = "SPMU_APPLICATION_STAGE_VALUE")
@Entity(name = "spmu_ApplicationStageValue")
public class ApplicationStageValue extends StandardEntity {

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_ACCOUNT_ID")
    protected PlanAccount planAccount;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTICLE_ID")
    protected PlanArticle article;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANALYTICA_ID")
    protected Account analytica;

    @Column(name = "YEAR_")
    protected Integer year;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEASURE_ID")
    protected Measure measure;

    @Column(name = "PRICE")
    protected Double price = 0.0;

    @Column(name = "AMOUNT")
    protected Double amount = 0.0;

    @Column(name = "TOTAL")
    protected Double total = 0.0;

    @Column(name = "VALUE_")
    protected Double value = 0.0;

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

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_STAGE_ID")
    protected ApplicationStage applicationStage;

    @MetaProperty
    @Composition
    @OneToMany(mappedBy = "parent")
    @OrderBy("year ASC")
    protected List<ApplicationStageValue> childs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    protected ApplicationStageValue parent;

    public ApplicationStageValue getParent() {
        return parent;
    }

    public void setParent(ApplicationStageValue parent) {
        this.parent = parent;
    }

    public ApplicationStage getApplicationStage() {
        return applicationStage;
    }

    public void setApplicationStage(ApplicationStage applicationStage) {
        this.applicationStage = applicationStage;
    }

    public void setPlanAccount(PlanAccount planAccount) {
        this.planAccount = planAccount;
    }

    public PlanAccount getPlanAccount() {
        return planAccount;
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Account getAnalytica() {
        return analytica;
    }

    public void setAnalytica(Account analytica) {
        this.analytica = analytica;
    }

    public PlanArticle getArticle() {
        return article;
    }

    public void setArticle(PlanArticle article) {
        this.article = article;
    }

    public List<ApplicationStageValue> getChilds() {
        return childs;
    }

    public void setChilds(List<ApplicationStageValue> childs) {
        this.childs = childs;
    }

}
