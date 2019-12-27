package com.company.spmu.service;

import com.company.spmu.entity.*;
import com.company.spmu.enumeration.PlanArticleType;
import com.haulmont.yarg.structure.ReportOutputType;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface AppStageService {
    String NAME = "spmu_AppStageService";

    int recalcAppStageOrder(Application parentApp);
    void moveUp(ApplicationStage s, Application parentApp);
    void moveDown(ApplicationStage s, Application parentApp);
    void copy(ApplicationStage a);
    byte[] printReportIncome(List<ApplicationStageIncome> items) throws IOException;
    byte[] printReportExpense(List<ApplicationStageExpense> items) throws IOException;
    byte[] printReportEvent(List<ApplicationStageEvent> items) throws IOException;
    void saveCurator(ApplicationStage ai, Employee employee);
    void saveManager(ApplicationStage ai, Employee employee);
    byte[] exportReportStageIncome(List<ApplicationStageIncome> selected, ReportOutputType outputType) throws IOException;
    byte[] exportReportStageEvent(List<ApplicationStageEvent> selected, ReportOutputType outputType) throws IOException;
    byte[] exportReportStageExpense(List<ApplicationStageExpense> selected, ReportOutputType outputType, PlanArticleType planArticleType) throws IOException;
}
