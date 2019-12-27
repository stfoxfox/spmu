package com.company.spmu.service;

import com.company.spmu.entity.*;
import com.haulmont.yarg.structure.ReportOutputType;

import java.io.IOException;
import java.util.List;

public interface AppExpenseService {
    String NAME = "spmu_AppExpenseService";

    void saveInitiator(Application ai, Employee employee);
    double recalcStagesSum(Application parentApp);
    void saveCurator(Application ai, Employee employee);
    void saveManager(Application ai, Employee employee);
    List<ApplicationDetailType> getAllDetailTypes();

    List<ApplicationStageValue> getParentList(ApplicationStageValue applicationStageValue);
    byte[] printReport(List<ApplicationExpense> selected) throws IOException;
    ApplicationExpense getExpenseWithParams(Integer year, PlanArticle planArticle, Division division);

    void removeStages(ApplicationExpense parent);

    List<PlanArticleStage> getAllPlanArticleStages(PlanArticle planArticle);

    boolean isStageCreatedFromPlanCanEdited(ApplicationStageExpense edit);
    byte[] exportReport(List<ApplicationExpense> selected, ReportOutputType outputType) throws IOException;
}
