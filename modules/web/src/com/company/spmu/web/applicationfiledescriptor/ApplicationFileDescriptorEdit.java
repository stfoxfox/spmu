package com.company.spmu.web.applicationfiledescriptor;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ApplicationFileDescriptor;

import java.util.Date;

@UiController("spmu_ApplicationFileDescriptor.edit")
@UiDescriptor("application-file-descriptor-edit.xml")
@EditedEntityContainer("applicationFileDescriptorDc")
@LoadDataBeforeShow
public class ApplicationFileDescriptorEdit extends StandardEditor<ApplicationFileDescriptor> {
    @Subscribe
    private void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        this.getEditedEntity().getFile().setUpdateTs(new Date());
    }
}
