package com.company.spmu.service;

import com.company.spmu.entity.*;
import com.company.spmu.enumeration.LinkType;
import com.company.spmu.repositories.entity.ApplicationLinkRepository;
import com.company.spmu.repositories.entity.ApplicationStageRepository;
import com.company.spmu.repositories.entity.ApplicationStageValueRepository;
import com.company.spmu.service.versioning.RestoreVersionService;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.global.DataManager;
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
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.haulmont.yarg.loaders.factory.DefaultLoaderFactory.JSON_DATA_LOADER;

@Service(AppEventService.NAME)
public class AppEventServiceBean implements AppEventService {


    @Inject
    private DataManager dataManager;
    @Inject
    private RestoreVersionService restoreVersionService;
    @Inject
    private ApplicationStageValueRepository applicationStageValueRepository;
    @Inject
    private ApplicationStageRepository applicationStageRepository;
    @Inject
    private ApplicationLinkRepository applicationLinkRepository;

    @Override
    public void recalculationMonths(ApplicationStage parent, ApplicationStageValue parentValue, Calendar oldStart, Calendar newStart, int stepDate) {


        List<ApplicationStageValue> listYears = applicationStageValueRepository.findByParent(parentValue);
        listYears.add(parentValue);
        int[][] calculatedMonths = new int[12][2];
        double[] valuesYear = new double[12];

        listYears.sort(Comparator.comparing(ApplicationStageValue::getYear));
        generateYears(listYears, parent);
        //обратный порядок для избежания перезаписи данных
        for (int i = listYears.size() - 1; i >= 0; i--) {

            //взятие значения с месяцев и установка его в ноль, для следующего возможного заполнения
            valuesYear[0] = listYears.get(i).getM1();
            valuesYear[1] = listYears.get(i).getM2();
            valuesYear[2] = listYears.get(i).getM3();
            valuesYear[3] = listYears.get(i).getM4();
            valuesYear[4] = listYears.get(i).getM5();
            valuesYear[5] = listYears.get(i).getM6();
            valuesYear[6] = listYears.get(i).getM7();
            valuesYear[7] = listYears.get(i).getM8();
            valuesYear[8] = listYears.get(i).getM9();
            valuesYear[9] = listYears.get(i).getM10();
            valuesYear[10] = listYears.get(i).getM11();
            valuesYear[11] = listYears.get(i).getM12();
            listYears.get(i).setM1(0.0);
            listYears.get(i).setM2(0.0);
            listYears.get(i).setM3(0.0);
            listYears.get(i).setM4(0.0);
            listYears.get(i).setM5(0.0);
            listYears.get(i).setM6(0.0);
            listYears.get(i).setM7(0.0);
            listYears.get(i).setM8(0.0);
            listYears.get(i).setM9(0.0);
            listYears.get(i).setM10(0.0);
            listYears.get(i).setM11(0.0);
            listYears.get(i).setM12(0.0);


            //перенос старого начала в новое начало
            int startMonths = 0;
            if (i == 0) {
                startMonths = oldStart.get(Calendar.MONTH); // месяц начала заявки

                //  куда переносить значения
                calculatedMonths[startMonths][0] = newStart.get(Calendar.MONTH);
                calculatedMonths[startMonths][1] = newStart.get(Calendar.YEAR);
            } else {
                startMonths = -1;
            }
            Calendar month = new GregorianCalendar();
            for (int j = startMonths + 1; j < 12; j++) {
                //установка последнего дня для каждого месяца
                month.clear();
                month.set(Calendar.MONTH, j);
                month.set(Calendar.YEAR, listYears.get(i).getYear());
                month.set(Calendar.DATE, month.getActualMaximum(Calendar.DAY_OF_MONTH));
                //перенос значений на шаг
                month.add(Calendar.DAY_OF_YEAR, stepDate);

                //  куда переносить значения
                calculatedMonths[j][0] = month.get(Calendar.MONTH);
                calculatedMonths[j][1] = month.get(Calendar.YEAR);
            }
            //установка значений в вычисленный перенос месяцев
            for (int j = 0; j < 12; j++) {
                this.checkYear(listYears, calculatedMonths[j][0], calculatedMonths[j][1], valuesYear[j], false);
            }


        }
        //сохранение изменений
        listYears.forEach(element -> dataManager.commit(element));

    }

    @Override
    public void recalculationQuarter(ApplicationStage parent, ApplicationStageValue parentValue, Calendar oldStart, Calendar newStart, int stepDate) {

        List<ApplicationStageValue> listYears = applicationStageValueRepository.findByParent(parentValue);
        listYears.add(parentValue);
        int[][] calculatedMonths = new int[4][2];
        double[] valuesYear = new double[4];


        listYears.sort(Comparator.comparing(ApplicationStageValue::getYear));
        generateYears(listYears, parent);
        //обратный порядок для избежания перезаписи данных
        for (int i = listYears.size() - 1; i >= 0; i--) {
            //взятие значения с кварталов и установка его в ноль, для следующего возможного заполнения
            valuesYear[0] = listYears.get(i).getQ1();
            valuesYear[1] = listYears.get(i).getQ2();
            valuesYear[2] = listYears.get(i).getQ3();
            valuesYear[3] = listYears.get(i).getQ4();
            listYears.get(i).setQ1(0.0);
            listYears.get(i).setQ2(0.0);
            listYears.get(i).setQ3(0.0);
            listYears.get(i).setQ4(0.0);

            //перенос старого начала в новое начало
            int startQuarter;
            if (i == 0) {
                startQuarter = oldStart.get(Calendar.MONTH) / 3; // месяц начала  заявки

                //  куда переносить значения
                calculatedMonths[startQuarter][0] = newStart.get(Calendar.MONTH);
                calculatedMonths[startQuarter][1] = newStart.get(Calendar.YEAR);
            } else {
                startQuarter = -1;
            }
            Calendar month = new GregorianCalendar();
            for (int j = startQuarter + 1; j < 4; j++) {
                //установка последнего дня для каждого квартала
                month.clear();
                month.set(Calendar.MONTH, 2 + j * 3);
                month.set(Calendar.YEAR, listYears.get(i).getYear());
                month.set(Calendar.DATE, month.getActualMaximum(Calendar.DAY_OF_MONTH));
                //перенос значений на шаг
                month.add(Calendar.DAY_OF_YEAR, stepDate);

                //  куда переносить значения
                calculatedMonths[j][0] = month.get(Calendar.MONTH);
                calculatedMonths[j][1] = month.get(Calendar.YEAR);

            }
            //установка значений в вычисленный перенос кварталов
            for (int j = 0; j < 4; j++) {
                this.checkYear(listYears, calculatedMonths[j][0], calculatedMonths[j][1], valuesYear[j], true);
            }

        }

        //сохранение изменений
        listYears.forEach(element -> dataManager.commit(element));

    }


    @Override
    public void generateYears(List<ApplicationStageValue> listYears, ApplicationStage parent) {
        Calendar year = new GregorianCalendar();
        year.setTime(parent.getStart());
        AtomicInteger start = new AtomicInteger(year.get(Calendar.YEAR));
        year.setTime(parent.getEnd());
        AtomicInteger end = new AtomicInteger(year.get(Calendar.YEAR));

        if (start.get() != end.get()) {
            listYears.forEach(elem -> {
                if (elem.getYear() == start.get() + 1) {
                    start.addAndGet(1);
                }
            });
            for (int i = start.get() + 1; i <= end.get(); i++) {
                ApplicationStageValue newAppvalue = new ApplicationStageValue();
                newAppvalue.setParent(listYears.get(0));
                newAppvalue.setYear(i);
                listYears.add(newAppvalue);
            }
        }

    }

    @Override
    public void checkYear(List<ApplicationStageValue> listYears, int dateMonth, int dateYear, double value, boolean isQuarter) {
        if (value == 0.0) {
            return;
        }
        if (isQuarter) {

            listYears.forEach(elem -> {
                if (elem.getYear() == dateYear) {
                    if (dateMonth / 3 == 3) {
                        elem.setQ4(value);
                    }
                    if (dateMonth / 3 == 2) {
                        elem.setQ3(value);
                    }
                    if (dateMonth / 3 == 1) {
                        elem.setQ2(value);
                    }
                    if (dateMonth / 3 == 0) {
                        elem.setQ1(value);
                    }
                }
            });
        } else {
            listYears.forEach(elem -> {
                if (elem.getYear() == dateYear) {
                    switch (dateMonth) {
                        case 0: {
                            elem.setM1(value);
                            break;
                        }
                        case 1: {
                            elem.setM2(value);
                            break;
                        }
                        case 2: {
                            elem.setM3(value);
                            break;
                        }
                        case 3: {
                            elem.setM4(value);
                            break;
                        }
                        case 4: {
                            elem.setM5(value);
                            break;
                        }
                        case 5: {
                            elem.setM6(value);
                            break;
                        }
                        case 6: {
                            elem.setM7(value);
                            break;
                        }
                        case 7: {
                            elem.setM8(value);
                            break;
                        }
                        case 8: {
                            elem.setM9(value);
                            break;
                        }
                        case 9: {
                            elem.setM10(value);
                            break;
                        }
                        case 10: {
                            elem.setM11(value);
                            break;
                        }
                        case 11: {
                            elem.setM12(value);
                            break;
                        }
                    }
                }
            });
        }
    }

    @Override
    public byte[] exportReport(List<ApplicationEvent> selected, ReportOutputType outputType) throws IOException {
        JSONObject data = this.getDataForExport(selected);
        ByteArrayOutputStream outputStream = this.getReport("appevents.docx", data, outputType);
        return outputStream.toByteArray();
    }

    /**
     * Формируем json объект с массивом данных для экспорта
     *
     * @param selected
     * @return
     */
    private JSONObject getDataForExport(List<ApplicationEvent> selected) {
        JSONObject data = new JSONObject();
        JSONArray arr = new JSONArray();
        selected.stream().forEach(app -> {
            JSONObject obj = new JSONObject();
            obj.put("name", app.getName());
            obj.put("code", app.getCode());
            Affilate af = app.getAffiliate();
            obj.put("affiliate", (af == null ? "" : af.getName()));
            DateFormat f = new SimpleDateFormat("dd.MM.yyyy");
            obj.put("datestart", (app.getDateStart() != null ? f.format(app.getDateStart()) : ""));
            obj.put("dateend", (app.getDateEnd() != null ? f.format(app.getDateEnd()) : ""));
            obj.put("price", app.getValue());
            arr.put(obj);
        });
        data.put("items", arr);
        return data;
    }

    /**
     * Получаем отчёт в нужном формате
     *
     * @param templateFileName
     * @param data
     * @param outputType
     * @return
     * @throws IOException
     */
    private ByteArrayOutputStream getReport(String templateFileName, JSONObject data, ReportOutputType outputType) throws IOException {
        ReportBuilder reportBuilder = new ReportBuilder();
        ReportTemplateBuilder reportTemplateBuilder = new ReportTemplateBuilder()
                .documentPath("../../../modules/global/src/com/company/spmu/reports/" + templateFileName)
                .documentName(templateFileName)
                .outputType(outputType)
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

    @Override
    public boolean change(ApplicationStage parentStage, ApplicationStage childStage, int step, LinkType linkType) {
        // для изменения значений напрямую не пускает
        ApplicationStage parent = applicationStageRepository.findOne(parentStage.getId());
        ApplicationStage child = applicationStageRepository.findOne(childStage.getId());
        ApplicationStageValue parentValue = applicationStageValueRepository.findByApplicationStage(parent).get(0);


        java.util.Calendar pStart = new GregorianCalendar();
        java.util.Calendar pNewStart = new GregorianCalendar();
        java.util.Calendar pEnd = new GregorianCalendar();

        pStart.setTime(parent.getStart());
        pNewStart.setTime(parent.getStart());
        pEnd.setTime(parent.getEnd());

        java.util.Calendar cStart = new GregorianCalendar();
        java.util.Calendar cEnd = new GregorianCalendar();

        cStart.setTime(child.getStart());
        cEnd.setTime(child.getEnd());

        final long MSPERDAY = 60 * 60 * 24 * 1000;
        int stepDate;
        switch (linkType) {
            case START_START: {
                stepDate = (int) ((cStart.getTimeInMillis() - pStart.getTimeInMillis()) / MSPERDAY + step);

                pNewStart.setTime(cStart.getTime());
                pNewStart.add(java.util.Calendar.DAY_OF_YEAR, step);
                pEnd.add(java.util.Calendar.DAY_OF_YEAR, stepDate);
                break;
            }
            case START_END: {
                stepDate = (int) ((cEnd.getTimeInMillis() - pStart.getTimeInMillis()) / MSPERDAY + (2 * step));
                pNewStart.setTime(cEnd.getTime());
                pEnd.setTime(cEnd.getTime());
                pEnd.add(java.util.Calendar.DAY_OF_YEAR, (int) ((pEnd.getTimeInMillis() - pStart.getTimeInMillis()) / MSPERDAY + step));

                break;
            }
            case END_END: {
                stepDate = (int) ((cEnd.getTimeInMillis() - pEnd.getTimeInMillis()) / MSPERDAY + step);
                pNewStart.add(java.util.Calendar.DAY_OF_YEAR, stepDate);
                pEnd.setTime(cEnd.getTime());
                pEnd.add(java.util.Calendar.DAY_OF_YEAR, step);
                break;
            }
            default: {
                return false;
            }
        }
        parent.setStart(pNewStart.getTime());
        parent.setEnd(pEnd.getTime());


        if (parent.getIs_quartal()) {
            this.recalculationQuarter(parent, parentValue, pStart, pNewStart, stepDate);
        } else {
            this.recalculationMonths(parent, parentValue, pStart, pNewStart, stepDate);
        }
        dataManager.commit(parent);
        return true;
    }

    @Override
    public void recalculationLinks(ApplicationStage applicationStage) {
        List<ApplicationLink> applicationLinkList = applicationLinkRepository.findByParentStage(applicationStage);

        // сортировка для соблюдения последовательности
        applicationLinkList.forEach(link -> link = dataManager.reload(link, "applicationLink-view"));
        applicationLinkList.sort(Comparator.comparing(ApplicationLink::getCreateTs));

        // первая версия ApplicationStage
        ApplicationStageEvent parentStage = (ApplicationStageEvent) applicationStageRepository.findOne(applicationStage.getId());
        ApplicationStageEvent firstStage = (ApplicationStageEvent) this.restoreVersionService.getFirst(ApplicationStageEvent.class, applicationStage.getId());
        //перенос значений дат
        parentStage.setStart(firstStage.getStart());
        parentStage.setEnd(firstStage.getEnd());
        dataManager.commit(parentStage);

        // первыя версия родилтельского applicationStageValue
        ApplicationStageValue applicationStageValue = applicationStageValueRepository.findOne(applicationStage.getApplicationStageValue().getId());
        //устанавливаем первые не нулевые значения, если таких нету то берется сама сущность т. к. в ней уже стоят нули
        for (int version = 1; version <= applicationStageValue.getVersion(); version++) {
            ApplicationStageValue firstStageValue = (ApplicationStageValue) this.restoreVersionService.getByVersion(ApplicationStageValue.class, applicationStage.getApplicationStageValue().getId(), version);

            if(firstStageValue.getValue() != 0){
                //перенос значений
                applicationStageValue.setValue(firstStageValue.getValue());
                applicationStageValue.setQ1(firstStageValue.getQ1());
                applicationStageValue.setQ2(firstStageValue.getQ2());
                applicationStageValue.setQ3(firstStageValue.getQ3());
                applicationStageValue.setQ4(firstStageValue.getQ4());

                applicationStageValue.setM1(firstStageValue.getM1());
                applicationStageValue.setM2(firstStageValue.getM2());
                applicationStageValue.setM3(firstStageValue.getM3());
                applicationStageValue.setM4(firstStageValue.getM4());
                applicationStageValue.setM5(firstStageValue.getM5());
                applicationStageValue.setM6(firstStageValue.getM6());
                applicationStageValue.setM7(firstStageValue.getM7());
                applicationStageValue.setM8(firstStageValue.getM8());
                applicationStageValue.setM9(firstStageValue.getM9());
                applicationStageValue.setM10(firstStageValue.getM10());
                applicationStageValue.setM11(firstStageValue.getM11());
                applicationStageValue.setM12(firstStageValue.getM12());
                break;
            }
        }

        dataManager.commit(applicationStageValue);

        // все элементы с родителем applicationStageValue
        List<ApplicationStageValue> applicationStageValueList = applicationStageValueRepository.findByParent(applicationStageValue);

        //  первые версии с родителем ApplicationStageValue
        applicationStageValueList.forEach(element -> {
            // если год вне диапозона то его удалять
            if(parentStage.getStart().getYear() >= element.getYear() && element.getYear() <= parentStage.getEnd().getYear()){
                for (int version = 0; version <= element.getVersion() ; version++) {
                    ApplicationStageValue firstStageValues= (ApplicationStageValue) this.restoreVersionService.getByVersion(ApplicationStageValue.class, element.getId(), version);

                    if(firstStageValues.getValue() != 0){

                        //перенос значений
                        element.setValue(firstStageValues.getValue());
                        element.setQ1(firstStageValues.getQ1());
                        element.setQ2(firstStageValues.getQ2());
                        element.setQ3(firstStageValues.getQ3());
                        element.setQ4(firstStageValues.getQ4());

                        element.setM1(firstStageValues.getM1());
                        element.setM2(firstStageValues.getM2());
                        element.setM3(firstStageValues.getM3());
                        element.setM4(firstStageValues.getM4());
                        element.setM5(firstStageValues.getM5());
                        element.setM6(firstStageValues.getM6());
                        element.setM7(firstStageValues.getM7());
                        element.setM8(firstStageValues.getM8());
                        element.setM9(firstStageValues.getM9());
                        element.setM10(firstStageValues.getM10());
                        element.setM11(firstStageValues.getM11());
                        element.setM12(firstStageValues.getM12());
                        dataManager.commit(element);
                        return;
                    }
                }
            } else {
                dataManager.remove(element);
            }
        });

        applicationLinkList.forEach(link -> {
            link = dataManager.reload(link, "applicationLink-view");
            this.change(parentStage, link.getChildStage(), link.getStep(), link.getLinkType());
        });


    }

}
