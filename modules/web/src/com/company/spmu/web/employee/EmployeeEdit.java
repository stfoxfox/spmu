package com.company.spmu.web.employee;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Employee;

@UiController("spmu_Employee.edit")
@UiDescriptor("employee-edit.xml")
@EditedEntityContainer("employeeDc")
@LoadDataBeforeShow
public class EmployeeEdit extends StandardEditor<Employee> {
}
