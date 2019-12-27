package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@PublishEntityChangedEvents
@Table(name = "SPMU_CONNECTION_LOG")
@Entity(name = "spmu_ConnectionLog")
public class ConnectionLog extends StandardEntity {
    private static final long serialVersionUID = -9161751460637772470L;

    @Lob
    @Column(name = "URL")
    protected String url;

    @Lob
    @Column(name = "REQUEST")
    protected String request;

    @Column(name = "HTTP_CODE")
    protected Integer httpCode;

    @Lob
    @Column(name = "RESPONSE")
    protected String response;

    public String getUrl() {
        return url;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public String getRequest() {
        return request;
    }

    public String getResponse() {
        return response;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}