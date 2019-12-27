package com.company.spmu.web.correctionhistory;


import com.company.spmu.entity.ApplicationStage;
import com.haulmont.cuba.gui.screen.UiController;

@UiController("spmu_ApplicationStageCorrectionHistoryFragment")
public class ApplicationStageCorrectionHistoryFragment extends EntityCorrectionHistoryBaseFragment<ApplicationStage> {
    @Override
    protected Object[] getMessageParams(String... params) {
        return new Object[]{entity.getName()};
    }
}
