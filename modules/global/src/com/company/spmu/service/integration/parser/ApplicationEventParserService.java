package com.company.spmu.service.integration.parser;

import com.company.spmu.entity.ApplicationEvent;
import org.json.JSONObject;

public interface ApplicationEventParserService {
    String NAME = "spmu_ApplicationEventService";

    public ApplicationEvent parse(JSONObject object);
}