package com.company.spmu.web.correctionhistory;


import com.company.spmu.entity.Application;
import com.haulmont.cuba.gui.screen.UiController;

@UiController("spmu_ApplicationCorrectionHistoryFragment")
public class ApplicationCorrectionHistoryFragment extends EntityCorrectionHistoryBaseFragment<Application> {
    @Override
    protected Object[] getMessageParams(String... params) {
        return new Object[]{entity.getCode() != null ? entity.getCode() : "",
                formatter.formatDate(entity.getDate())};
    }
}
