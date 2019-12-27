package com.company.spmu.listeners;

import com.company.spmu.entity.Employee;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("spmu_EmployeeChangedListener")
public class EmployeeChangedListener extends AbstractBaseListener<Employee> {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void afterCommit(EntityChangedEvent<Employee, UUID> event) {
        perform(event.getEntityId().getValue());
    }

    @Override
    protected Class<Employee> getEntityClass() {
        return Employee.class;
    }
}


