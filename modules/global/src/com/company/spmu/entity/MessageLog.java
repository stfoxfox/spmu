package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.Entity;
import javax.persistence.Table;

@PublishEntityChangedEvents
@Table(name = "SPMU_MESSAGE_LOG")
@Entity(name = "spmu_MessageLog")
public class MessageLog extends StandardEntity {
    private static final long serialVersionUID = -7990135779674906128L;
    public static final long LAST_RECORD_IN_UIM = 1;
}