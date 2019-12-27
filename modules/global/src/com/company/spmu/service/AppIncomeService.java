package com.company.spmu.service;

import com.company.spmu.entity.Application;
import com.company.spmu.entity.ApplicationDetailType;
import com.company.spmu.entity.ApplicationStageValue;
import com.company.spmu.entity.ApplicationIncome;
import com.company.spmu.entity.Employee;
import com.haulmont.yarg.structure.ReportOutputType;

import java.io.IOException;
import java.util.List;

public interface AppIncomeService {
    String NAME = "spmu_AppIncomeService";

    void saveInitiator(Application ai, Employee employee);
    void recalcStagesSum(Application parentApp);
    void saveCurator(Application ai, Employee employee);
    void saveManager(Application ai, Employee employee);
    List<ApplicationDetailType> getAllDetailTypes();

    List<ApplicationStageValue> getParentList(ApplicationStageValue applicationStageValue);
    byte[] printReport(List<ApplicationIncome> selected) throws IOException;
    byte[] exportReport(List<ApplicationIncome> selected, ReportOutputType outputType) throws IOException;
}
