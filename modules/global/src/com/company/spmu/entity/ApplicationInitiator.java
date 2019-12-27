package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * инициатор заявки
 * @see ApplicationResponsible
 */
@PublishEntityChangedEvents
@Entity(name = "spmu_ApplicationInitiator")
@DiscriminatorValue(value="I")
public class ApplicationInitiator extends ApplicationResponsible {
    /**
     * TODO: возможно нужно организовать поиск уже существующей записи
     */
    public static ApplicationInitiator getNew(Application a, Employee e) {
        ApplicationInitiator initiator = new ApplicationInitiator();
        initiator.setApplication(a);
        initiator.setEmployee(e);
        return initiator;
    }

}
