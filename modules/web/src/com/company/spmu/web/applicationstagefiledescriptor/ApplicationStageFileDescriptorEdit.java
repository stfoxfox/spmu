package com.company.spmu.web.applicationstagefiledescriptor;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.ApplicationStageFileDescriptor;

import java.util.Date;

@UiController("spmu_ApplicationStageFileDescriptor.edit")
@UiDescriptor("application-stage-file-descriptor-edit.xml")
@EditedEntityContainer("applicationStageFileDescriptorDc")
@LoadDataBeforeShow
public class ApplicationStageFileDescriptorEdit extends StandardEditor<ApplicationStageFileDescriptor> {
    @Subscribe
    private void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        this.getEditedEntity().getFile().setUpdateTs(new Date());
    }
    
    
}
