package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * ответственный куратор в филиале
 * @see ApplicationResponsible
 */
@PublishEntityChangedEvents
@Entity(name = "spmu_ApplicationManager")
@DiscriminatorValue(value="M")
public class ApplicationManager extends ApplicationResponsible {
    /**
     * TODO: возможно нужно организовать поиск уже существующей записи
     */
    public static ApplicationManager getNew(Application a, Employee e) {
        ApplicationManager manager = new ApplicationManager();
        manager.setApplication(a);
        manager.setEmployee(e);
        return manager;
    }
}
