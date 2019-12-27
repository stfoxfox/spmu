package com.company.spmu.service.versioning;

import com.haulmont.bpm.entity.ProcTask;

public interface ApplicationHistoryService {
    String NAME = "spmu_ApplicationHistoryService";

    void store(ProcTask procTask);
}