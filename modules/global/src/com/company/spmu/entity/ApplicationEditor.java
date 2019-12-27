package com.company.spmu.entity;

import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * редактор заявки
 * @see ApplicationResponsible
 */
@PublishEntityChangedEvents
@Entity(name = "spmu_ApplicationEditor")
@DiscriminatorValue(value="E")
public class ApplicationEditor extends ApplicationResponsible {

}
