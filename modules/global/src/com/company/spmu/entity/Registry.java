package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import java.util.Date;

@PublishEntityChangedEvents
@Table(name = "SPMU_REGISTRY")
@Entity(name = "spmu_Registry")
public class Registry extends StandardEntity {
    @Column(name = "CODE")
    protected String code;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_")
    protected Date date;

    @Column(name = "TO_UIM")
    protected Boolean toUim;

    @Column(name = "PROCESSED")
    protected Boolean processed;

    @Column(name = "REPLY_STATUS")
    protected Integer reply_status;

    @Lob
    @Column(name = "REPLY_MESSAGE")
    protected String reply_message;

    @Lob
    @Column(name = "REPLY_RESULT")
    protected String reply_result;

    @Lob
    @Column(name = "MESSAGE")
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReply_result() {
        return reply_result;
    }

    public void setReply_result(String reply_result) {
        this.reply_result = reply_result;
    }

    public String getReply_message() {
        return reply_message;
    }

    public void setReply_message(String reply_message) {
        this.reply_message = reply_message;
    }

    public Integer getReply_status() {
        return reply_status;
    }

    public void setReply_status(Integer reply_status) {
        this.reply_status = reply_status;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public Boolean getToUim() {
        return toUim;
    }

    public void setToUim(Boolean toUim) {
        this.toUim = toUim;
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
}