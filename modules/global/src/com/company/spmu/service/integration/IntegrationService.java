package com.company.spmu.service.integration;

public interface IntegrationService {
    String NAME = "spmu_IntegrationService";

    public String getData(String url);
    public void sendData(String data, String additionalUrl);
}