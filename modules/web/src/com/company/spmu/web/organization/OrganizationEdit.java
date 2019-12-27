package com.company.spmu.web.organization;

import com.company.spmu.web.custom.editors.UuidStandardEditor;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Organization;

@UiController("spmu_Organization.edit")
@UiDescriptor("organization-edit.xml")
@EditedEntityContainer("organizationDc")
@LoadDataBeforeShow
public class OrganizationEdit extends UuidStandardEditor<Organization> {
}