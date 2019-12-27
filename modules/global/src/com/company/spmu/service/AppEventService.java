package com.company.spmu.service;

import com.company.spmu.entity.ApplicationEvent;
import com.company.spmu.entity.ApplicationLink;
import com.company.spmu.entity.ApplicationStage;
import com.company.spmu.entity.ApplicationStageValue;
import com.company.spmu.enumeration.LinkType;
import com.haulmont.yarg.structure.ReportOutputType;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public interface AppEventService {
    String NAME = "spmu_AppEventService";

    void recalculationMonths(ApplicationStage parent, ApplicationStageValue parentValue, Calendar oldStart, Calendar newStart, int stepDate);

    void recalculationQuarter(ApplicationStage parent, ApplicationStageValue parentValue, Calendar oldStart, Calendar newStart, int stepDate);

    void generateYears(List<ApplicationStageValue> listYears, ApplicationStage parent);

    void checkYear(List<ApplicationStageValue> listYears, int dateMonth, int dateYear, double value, boolean isQuarter);
    byte[] exportReport(List<ApplicationEvent> selected, ReportOutputType outputType) throws IOException;

    boolean change(ApplicationStage parentStage, ApplicationStage childStage, int step, LinkType linkType);

    void recalculationLinks(ApplicationStage applicationStage);
}
