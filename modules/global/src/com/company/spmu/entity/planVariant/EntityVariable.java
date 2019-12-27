package com.company.spmu.entity.planVariant;

import com.company.spmu.entity.planVariant.interfaces.IVariable;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "SPMU_ENTITY_VARIABLE")
@Entity(name = "spmu_EntityVariable")
public class EntityVariable extends StandardEntity implements IVariable {
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPUTED_VARIABLE_ID")
    protected ComputedVariable computedVariable;

    @Column(name = "ENTITY_NAME")
    protected String entityName;

    @Column(name = "ENTITY_ATTRIBUTE")
    protected String entityAttribute;

    @Column(name = "POSITION")
    protected Integer position;

    @Transient
    protected UUID entityId;

    public ComputedVariable getComputedVariable() {
        return computedVariable;
    }

    public void setComputedVariable(ComputedVariable computedVariable) {
        this.computedVariable = computedVariable;
    }

    public String getEntityAttribute() {
        return entityAttribute;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityAttribute(String entityAttribute) {
        this.entityAttribute = entityAttribute;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public UUID getEntityId() {
        return entityId;
    }

    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}