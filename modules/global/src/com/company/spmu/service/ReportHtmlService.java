package com.company.spmu.service;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface ReportHtmlService {
    String NAME = "spmu_ReportHtmlService";

    ByteArrayOutputStream getPrintReport(String templateFileName, JSONObject data) throws IOException;
}
