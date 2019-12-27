package com.company.spmu.service;

import com.haulmont.yarg.formatters.factory.DefaultFormatterFactory;
import com.haulmont.yarg.loaders.factory.DefaultLoaderFactory;
import com.haulmont.yarg.loaders.impl.GroovyDataLoader;
import com.haulmont.yarg.loaders.impl.JsonDataLoader;
import com.haulmont.yarg.reporting.Reporting;
import com.haulmont.yarg.reporting.RunParams;
import com.haulmont.yarg.structure.Report;
import com.haulmont.yarg.structure.ReportOutputType;
import com.haulmont.yarg.structure.impl.BandBuilder;
import com.haulmont.yarg.structure.impl.ReportBuilder;
import com.haulmont.yarg.structure.impl.ReportTemplateBuilder;
import com.haulmont.yarg.util.groovy.DefaultScriptingImpl;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.haulmont.yarg.loaders.factory.DefaultLoaderFactory.JSON_DATA_LOADER;

@Service(ReportHtmlService.NAME)
public class ReportHtmlServiceBean implements ReportHtmlService {

    @Override
    public ByteArrayOutputStream getPrintReport(String templateFileName, JSONObject data) throws IOException {
        ReportBuilder reportBuilder = new ReportBuilder();
        ReportTemplateBuilder reportTemplateBuilder = new ReportTemplateBuilder()
                .documentPath("../../../modules/global/src/com/company/spmu/reports/" + templateFileName)
                .documentName(templateFileName)
                .outputType(ReportOutputType.html)
                .readFileFromPath();
        reportBuilder.template(reportTemplateBuilder.build());

        reportBuilder.band(new BandBuilder().name("items")
                .query("items", "parameter=param1 $.items[*]", JSON_DATA_LOADER).build());

        Report report = reportBuilder.build();

        Reporting reporting = new Reporting();
        reporting.setFormatterFactory(new DefaultFormatterFactory());
        reporting.setLoaderFactory(new DefaultLoaderFactory()
                .setGroovyDataLoader(new GroovyDataLoader(new DefaultScriptingImpl()))
                .setJsonDataLoader(new JsonDataLoader()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        reporting.runReport(new RunParams(report).param("param1", data),
                outputStream);
        return outputStream;
    }
}