package com.company.spmu.service.integration.export;

import com.company.spmu.entity.Application;

public interface ApplicationEventExportService {
    String NAME = "spmu_ApplicationEventExportService";
    String ADDITIONAL_URL = "hs/RegisterMessages";

    public String getExportJsonString(Application application);
}