package com.company.spmu.web.screens.applicationstagetype;

import com.company.spmu.entity.PlanType;
import com.company.spmu.entity.SpmuUser;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ApplicationStageType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;

@UiController("spmu_ApplicationStageType.edit")
@UiDescriptor("application-stage-type-edit.xml")
@EditedEntityContainer("applicationStageTypeDc")
@LoadDataBeforeShow
public class ApplicationStageTypeEdit extends StandardEditor<ApplicationStageType> {
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
    protected void setCreatedDetails(InitEntityEvent<ApplicationStageType> event) {
        event.getEntity().setCreated(timeSource.currentTimestamp());
        event.getEntity().setCreator((SpmuUser) userSessionSource.getUserSession().getUser());
    }
}