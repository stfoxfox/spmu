package com.company.spmu.web.employee;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Employee;

@UiController("spmu_Employee.browse")
@UiDescriptor("employee-browse.xml")
@LookupComponent("employeesTable")
@LoadDataBeforeShow
public class EmployeeBrowse extends StandardLookup<Employee> {
}
