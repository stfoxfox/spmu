package com.company.spmu.web.custom.editors;

import com.company.spmu.entity.interfaces.HasGuid;
import com.haulmont.cuba.core.global.UuidSource;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;

import javax.inject.Inject;

public class UuidStandardEditor<T extends HasGuid> extends StandardEditor {
    @Inject
    private UuidSource uuidSource;

    @Subscribe
    private void onInitEntity(InitEntityEvent event) {
        HasGuid editedEntity = (HasGuid) event.getEntity();
        editedEntity.setGuid(uuidSource.createUuid());
    }
}
