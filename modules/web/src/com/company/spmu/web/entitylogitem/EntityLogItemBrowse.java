package com.company.spmu.web.entitylogitem;

import com.company.spmu.entity.Application;
import com.company.spmu.web.OptionScreenStage;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.EntityLogItem;

import javax.inject.Inject;

@UiController("sec$EntityLog.browse")
@UiDescriptor("entity-log-item-browse.xml")
@LookupComponent("entityLogItemsTable")
@LoadDataBeforeShow
public class EntityLogItemBrowse extends StandardLookup<EntityLogItem> {
    @Inject
    private CollectionLoader<EntityLogItem> entityLogItemsDl;

    @Subscribe
    private void onInit(InitEvent event) {
        ScreenOptions options = event.getOptions();
        if (options instanceof OptionScreenStage) {
            Application app = ((OptionScreenStage) options).getApplication();
            this.entityLogItemsDl.setParameter("id", app.getId());
            this.entityLogItemsDl.load();
        }
    }

}
