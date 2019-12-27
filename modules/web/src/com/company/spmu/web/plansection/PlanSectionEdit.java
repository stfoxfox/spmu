package com.company.spmu.web.plansection;

import com.company.spmu.entity.PlanType;
import com.company.spmu.entity.SpmuUser;
import com.company.spmu.web.custom.editors.UuidStandardEditor;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.PlanSection;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;

@UiController("spmu_PlanSection.edit")
@UiDescriptor("plan-section-edit.xml")
@EditedEntityContainer("planSectionDc")
@LoadDataBeforeShow
public class PlanSectionEdit extends UuidStandardEditor<PlanSection> {
    @Inject
    protected TimeSource timeSource;
    @Inject
    protected UserSessionSource userSessionSource;

    @Named("createdField")
    private DateField<Date> createdDate;
    @Named("creatorField")
    private LookupField<SpmuUser> creator;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        if (PersistenceHelper.isNew(getEditedEntity())) {
            createdDate.setVisible(false);
            creator.setVisible(false);
        }
    }

    @Subscribe
    protected void setCreatedDetails(InitEntityEvent<PlanSection> event) {
        event.getEntity().setCreated(timeSource.currentTimestamp());
        event.getEntity().setCreator((SpmuUser) userSessionSource.getUserSession().getUser());
    }
}