package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


/**
 * ответственный куратор в ГД
 * @see ApplicationResponsible
 */
@PublishEntityChangedEvents
@Entity(name = "spmu_ApplicationCurator")
@DiscriminatorValue(value="C")
public class ApplicationCurator extends ApplicationResponsible {
    public ApplicationCurator() {
        super();
    }

    /**
     * TODO: возможно нужно организовать поиск уже существующей записи
     */
    public static ApplicationCurator getNew(Application a, Employee e) {
        ApplicationCurator curator = new ApplicationCurator();
        curator.setApplication(a);
        curator.setEmployee(e);
        return curator;
    }
}
