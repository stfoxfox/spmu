package com.company.spmu.web.organization;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Organization;

@UiController("spmu_Organization.browse")
@UiDescriptor("organization-browse.xml")
@LookupComponent("organizationsTable")
@LoadDataBeforeShow
public class OrganizationBrowse extends StandardLookup<Organization> {
}