package com.company.spmu.service;

import com.company.spmu.entity.Application;
import com.company.spmu.entity.ApplicationStage;
import com.company.spmu.entity.ApplicationStageValue;
import com.company.spmu.entity.*;
import com.company.spmu.enumeration.ApplicationResponsibleRole;
import com.company.spmu.enumeration.PlanArticleType;
import com.company.spmu.repositories.entity.ApplicationStageRepository;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.UuidProvider;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.haulmont.yarg.loaders.factory.DefaultLoaderFactory.JSON_DATA_LOADER;

@Service(AppStageService.NAME)
public class AppStageServiceBean implements AppStageService {

    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;
    @Inject
    private DataManager dataManager;
    @Inject
    private ReportHtmlService reportHtmlService;
    @Inject
    private ApplicationStageRepository applicationStageRepository;
    /**
     * Сортировка статей заявки на доходы по order, на случай если там открываются тестовые несортированные данные
     * @param {Application} parentApp
     */
    @Override
    public int recalcAppStageOrder(Application parentApp) {
        // TODO: необходима блокировка на запись таблицы
        List<ApplicationStage> list = applicationStageRepository.findByApplication(parentApp);
        AtomicInteger i = new AtomicInteger(1);
        list.forEach(applicationStage -> applicationStage.setOrder(i.getAndIncrement()));
        list.forEach(element -> dataManager.commit(element));
        return i.get();
    }

    /**
     * Уменьшить order
     * @param {ApplicationStage} a
     * @param {Application} parentApp
     */
    @Override
    public void moveUp(ApplicationStage s, Application parentApp) {
        this.moveRow(s, parentApp, -1);
    }
    /**
     * Увеличить order
     * @param {ApplicationStage} a
     */
    @Override
    public void moveDown(ApplicationStage s, Application parentApp) {
        this.moveRow(s, parentApp, 1);
    }

    /**
     * Создать дубликат статьи
     * @param {ApplicationStage} a
     */
    @Override
    public void copy(ApplicationStage a) {
        CommitContext context = new CommitContext();
        ApplicationStage result = this.metadata.getTools().deepCopy(a);
        if (result != null) {
            result.setId(UuidProvider.createUuid());
            context.addInstanceToCommit(result);
            this.dataManager.commit(result);
        }
        this.recalcAppStageOrder(a.getApplication());
    }

    /**
     * Переместить строку вверх\вниз
     * @param {ApplicationStage} s
     * @param {Application} parentApp
     * @param {Int} direction
     */
    private void moveRow(ApplicationStage s, Application parentApp, int direction) {
        // TODO: необходима блокировка на запись таблицы
        try (Transaction tx = this.persistence.createTransaction()) {
            EntityManager entityManager = this.persistence.getEntityManager();
            Query query = entityManager.createNativeQuery("select * FROM SPMU_APPLICATION_STAGE where application_id=?1 order by order_", ApplicationStage.class);
            query.setParameter(1, parentApp.getId());
            List<ApplicationStage> list = query.getResultList();
            int i = 1;
            int orderTarget = s.getOrder() + direction;
            if (orderTarget < 1) return;
            for (Iterator it = list.iterator(); it.hasNext(); ) {
                ApplicationStage a = (ApplicationStage) it.next();
                if (i == orderTarget) {
                    a.setOrder(i - direction);
                } else if (a.getId().equals(s.getId())) {
                    s = a;
                } else {
                    a.setOrder(i);
                }
                i++;
            }
            if (orderTarget >= i) orderTarget = i - 1;
            s.setOrder(orderTarget);
            tx.commit();
        }
    }

    @Override
    public byte[] printReportExpense(List<ApplicationStageExpense> selected) throws IOException {
        JSONObject data = this.getDataExpenseForExport(selected);

        ByteArrayOutputStream outputStream = this.reportHtmlService.getPrintReport("appexpensestages.html", data);
        return outputStream.toByteArray();
    }

    @Override
    public byte[] printReportIncome(List<ApplicationStageIncome> selected) throws IOException {
        JSONObject data = this.getDataStageIncomeForExport(selected);
        ByteArrayOutputStream outputStream = this.reportHtmlService.getPrintReport("appincomestages.html", data);
        return outputStream.toByteArray();
    }

    @Override
    public byte[] printReportEvent(List<ApplicationStageEvent> selected) throws IOException {
        JSONObject data = this.getDataEventForExport(selected);

        ByteArrayOutputStream outputStream = this.reportHtmlService.getPrintReport("appeventstages.html", data);
        return outputStream.toByteArray();
    }

    private void saveResponsible(ApplicationStage ai, Employee employee, Class ResponsibleClass, ApplicationResponsibleRole role) {
        try (Transaction tx = this.persistence.createTransaction()) {
            EntityManager entityManager = this.persistence.getEntityManager();
            Query query = entityManager.createNativeQuery("select * FROM SPMU_APPLICATION_STAGE_RESPONSIBLE where APPLICATIONSTAGE_ID=?2 and ROLE=?4",
                    ApplicationStageResponsible.class);
            query.setParameter(2, ai.getId());
            query.setParameter(4, role);
            List<?> list = query.getResultList();
            if (list.size() == 0) {
                Method m = ResponsibleClass.getDeclaredMethod("getNew", ApplicationStage.class, Employee.class);
                m.setAccessible(true);
                Object ae = ResponsibleClass.cast(m.invoke(null, ai, employee));
                this.dataManager.commit((Entity) ae);
            } else {
                ApplicationStageResponsible found = null;
                for (Iterator it = list.iterator(); it.hasNext(); ) {
                    ApplicationStageResponsible a = (ApplicationStageResponsible) it.next();
                    if (a != null) {
                        if (a.getEmployee() != null) {
                            if (a.getEmployee().equals(employee)) {
                                found = a;
                                break;
                            }
                        }
                    }
                }
                if (found == null) {
                    Method m = ResponsibleClass.getDeclaredMethod("getNew", ApplicationStage.class, Employee.class);
                    m.setAccessible(true);
                    found = (ApplicationStageResponsible) m.invoke(null, ai, employee);
                    this.dataManager.commit((Entity) found);
                }
                Query query2 = entityManager.createNativeQuery("delete FROM SPMU_APPLICATION_RESPONSIBLE where APPLICATION_ID=?2 and ROLE=?4 and id!=?5");
                query2.setParameter(2, ai.getId());
                query2.setParameter(4, role);
                query2.setParameter(5, found.getId());
                query2.executeUpdate();
            }
            tx.commit();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveCurator(ApplicationStage ai, Employee employee) {
        this.saveResponsible(ai, employee, ApplicationStageCurator.class, ApplicationResponsibleRole.CURATOR);
    }

    @Override
    public void saveManager(ApplicationStage ai, Employee employee) {
        this.saveResponsible(ai, employee, ApplicationStageManager.class, ApplicationResponsibleRole.MANAGER);
    }

    /**
     * Формируем json объект с массивом данных для экспорта статей
     * @param selected
     * @return
     */
    private JSONObject getDataStageIncomeForExport(List<ApplicationStageIncome> selected) {
        JSONObject data = new JSONObject();
        JSONArray arr = new JSONArray();

        JSONObject obj2 = new JSONObject();
        obj2.put("num", "№");
        obj2.put("vpo", "Статья ВПО");
        obj2.put("kuu", "КУУ");
        obj2.put("kvu", "КВУ");
        obj2.put("name", "Наименование группы доходов");
        obj2.put("details", "Детализация предмета дохода");
        obj2.put("order", "Договор №");
        obj2.put("price", "Общая стоимость, тыс. р.");
        final int[] i = {0};
        JSONObject finalObj = obj2;
        selected.get(0).getApplicationStageValue().getChilds().stream().forEach(row -> {
            if (i[0] == 0) {
                finalObj.put("year0", "Запланировано в пред. периоды");
            } else {
                finalObj.put("year" + i[0], row.getYear());
            }
            i[0]++;
        });
        arr.put(obj2);

        selected.stream().forEach(app -> {
            JSONObject obj = new JSONObject();

            obj.put("num", app.getOrder());

            PlanArticle p = app.getPlanArticle();
            obj.put("vpo", (p == null ? "" : p.getName()));

            ApplicationStageValue v = app.getApplicationStageValue();
            PlanAccount a = null;
            if (v != null) {
                a = v.getPlanAccount();
            }
            obj.put("kuu", (v == null || a == null ? "" : a.getName()));

            Account a2 = null;
            if (v != null) {
                a2 = v.getAnalytica();
            }
            obj.put("kvu", (v == null || a == null ? "" : a2.getName()));

            obj.put("name", app.getName());
            obj.put("details", app.getDescription());

            ApplicationStageContract c = app.getApplicationStageContract();
            ContractStage c2 = null;
            if (c != null) {
                c2 = c.getContract();
            }
            obj.put("order", (c == null || c2 == null ? "" : c2.getName()));
            obj.put("price", (v == null ? "" : v.getValue()));

            i[0] = 0;
            app.getApplicationStageValue().getChilds().stream().forEach(year -> {
                String n2 = "year" + i[0];
                obj.put(n2, year.getValue());
                i[0]++;
            });

            arr.put(obj);
        });
        data.put("items", arr);
        return data;
    }

    @Override
    public byte[] exportReportStageIncome(List<ApplicationStageIncome> selected, ReportOutputType outputType) throws IOException {
        JSONObject data = this.getDataStageIncomeForExport(selected);
        ByteArrayOutputStream outputStream = this.getReport("appstageincomes.docx", data, outputType);
        return outputStream.toByteArray();
    }

    /**
     * Получаем отчёт в нужном формате
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

    private JSONObject getDataEventForExport(List<ApplicationStageEvent> selected) {
        JSONObject data = new JSONObject();
        JSONArray arr = new JSONArray();
        selected.stream().forEach(app -> {
            JSONObject obj = new JSONObject();
            PlanArticle p = app.getPlanArticle();
            obj.put("vpo", (p == null ? "" : p.getName()));

            ApplicationStageValue v = app.getApplicationStageValue();
            PlanAccount a = null;
            if (v != null) {
                a = v.getPlanAccount();
            }
            obj.put("kuu", (v == null || a == null ? "" : a.getName()));

            Account a2 = null;
            if (v != null) {
                a2 = v.getAnalytica();
            }
            obj.put("kvu", (v == null || a == null ? "" : a2.getName()));

            obj.put("name", app.getName());
            obj.put("descr", app.getDescription());
            obj.put("name", app.getOrder());
            obj.put("price", (v == null ? "" : v.getValue()));
            arr.put(obj);
        });
        data.put("items", arr);
        return data;
    }

    private JSONObject getDataExpenseForExport(List<ApplicationStageExpense> selected) {
        JSONObject data = new JSONObject();
        JSONArray arr = new JSONArray();
        selected.stream().forEach(app -> {
            JSONObject obj = new JSONObject();
            PlanArticle p = app.getPlanArticle();
            obj.put("vpo", (p == null ? "" : p.getName()));

            ApplicationStageValue v = app.getApplicationStageValue();
            PlanAccount a = null;
            if (v != null) {
                a = v.getPlanAccount();
            }
            obj.put("kuu", (v == null || a == null ? "" : a.getName()));

            Account a2 = null;
            if (v != null) {
                a2 = v.getAnalytica();
            }
            obj.put("kvu", (v == null || a == null ? "" : a2.getName()));

            obj.put("name", app.getName());
            obj.put("descr", app.getDescription());
            obj.put("name", app.getOrder());
            obj.put("price", (v == null ? "" : v.getValue()));
            arr.put(obj);
        });
        data.put("items", arr);
        return data;
    }

    @Override
    public byte[] exportReportStageEvent(List<ApplicationStageEvent> selected, ReportOutputType outputType) throws IOException {
        JSONObject data = this.getDataStageEventForExport(selected);
        ByteArrayOutputStream outputStream = this.getReport("appstageevents.docx", data, outputType);
        return outputStream.toByteArray();
    }

    private JSONObject getDataStageEventForExport(List<ApplicationStageEvent> selected) {
        JSONObject data = new JSONObject();
        JSONArray arr = new JSONArray();

        JSONObject obj2 = new JSONObject();
        obj2.put("num", "№");
        obj2.put("vpo", "Статья ВПО");
        obj2.put("kuu", "КУУ");
        obj2.put("kvu", "КВУ");
        obj2.put("name", "Наименование группы доходов");
        obj2.put("details", "Детализация предмета дохода");
        obj2.put("order", "Договор №");
        obj2.put("price", "Общая стоимость, тыс. р.");
        final int[] i = {0};
        JSONObject finalObj = obj2;
        selected.get(0).getApplicationStageValue().getChilds().stream().forEach(row -> {
            if (i[0] == 0) {
                finalObj.put("year0", "Запланировано в пред. периоды");
            } else {
                finalObj.put("year" + i[0], row.getYear());
            }
            i[0]++;
        });
        arr.put(obj2);

        selected.stream().forEach(app -> {
            JSONObject obj = new JSONObject();

            obj.put("num", app.getOrder());

            PlanArticle p = app.getPlanArticle();
            obj.put("vpo", (p == null ? "" : p.getName()));

            ApplicationStageValue v = app.getApplicationStageValue();
            PlanAccount a = null;
            if (v != null) {
                a = v.getPlanAccount();
            }
            obj.put("kuu", (v == null || a == null ? "" : a.getName()));

            Account a2 = null;
            if (v != null) {
                a2 = v.getAnalytica();
            }
            obj.put("kvu", (v == null || a == null || a2 == null ? "" : a2.getName()));

            obj.put("name", app.getName());
            obj.put("details", app.getDescription());

            ApplicationStageContract c = app.getApplicationStageContract();
            ContractStage c2 = null;
            if (c != null) {
                c2 = c.getContract();
            }
            obj.put("order", (c == null || c2 == null ? "" : c2.getName()));
            obj.put("price", (v == null ? "" : v.getValue()));

            i[0] = 0;
            app.getApplicationStageValue().getChilds().stream().forEach(year -> {
                String n2 = "year" + i[0];
                obj.put(n2, year.getValue());
                i[0]++;
            });

            arr.put(obj);
        });
        data.put("items", arr);
        return data;
    }

    @Override
    public byte[] exportReportStageExpense(List<ApplicationStageExpense> selected, ReportOutputType outputType, PlanArticleType planArticleType) throws IOException {
        JSONObject data = this.getDataStageExpenseForExport(selected, planArticleType);
        switch (planArticleType) {
            case CODE_20120:
                return this.getReport("appstageexpensesvpo20120.docx", data, outputType).toByteArray();
            case CODE_20200:
                return this.getReport("appstageexpensesvpo20200.docx", data, outputType).toByteArray();
            case CODE_20301:
                return this.getReport("appstageexpensesvpo20301.docx", data, outputType).toByteArray();
            case CODE_20302:
                return this.getReport("appstageexpensesvpo20302.docx", data, outputType).toByteArray();
            case CODE_20303:
                return this.getReport("appstageexpensesvpo20303.docx", data, outputType).toByteArray();
            case CODE_20304:
                return this.getReport("appstageexpensesvpo20304.docx", data, outputType).toByteArray();
            case CODE_20305:
                return this.getReport("appstageexpensesvpo20305.docx", data, outputType).toByteArray();
            case CODE_20306:
                return this.getReport("appstageexpensesvpo20306.docx", data, outputType).toByteArray();
            case CODE_20307:
                return this.getReport("appstageexpensesvpo20307.docx", data, outputType).toByteArray();
            case CODE_20308:
                return this.getReport("appstageexpensesvpo20308.docx", data, outputType).toByteArray();
            case CODE_20309:
                return this.getReport("appstageexpensesvpo20309.docx", data, outputType).toByteArray();
            case CODE_20310:
                return this.getReport("appstageexpensesvpo20310.docx", data, outputType).toByteArray();
            case CODE_20401:
                return this.getReport("appstageexpensesvpo20401.docx", data, outputType).toByteArray();
            case CODE_20402:
                return this.getReport("appstageexpensesvpo20402.docx", data, outputType).toByteArray();
            case CODE_20403:
                return this.getReport("appstageexpensesvpo20403.docx", data, outputType).toByteArray();
            case CODE_20404:
                return this.getReport("appstageexpensesvpo20404.docx", data, outputType).toByteArray();
            case CODE_20405:
                return this.getReport("appstageexpensesvpo20405.docx", data, outputType).toByteArray();
            case CODE_20406:
                return this.getReport("appstageexpensesvpo20406.docx", data, outputType).toByteArray();
            case CODE_20407:
                return this.getReport("appstageexpensesvpo20407.docx", data, outputType).toByteArray();
            case CODE_20408:
                return this.getReport("appstageexpensesvpo20408.docx", data, outputType).toByteArray();
            case CODE_20500:
                return this.getReport("appstageexpensesvpo20500.docx", data, outputType).toByteArray();
            default:
                return this.getReport("defaultappstageexpenses.docx", data, outputType).toByteArray();
        }
    }

    /**
     * Формируем json объект с массивом данных для экспорта статей
     * @param selected
     * @return
     */
    private JSONObject getDataStageExpenseForExport(List<ApplicationStageExpense> selected,  PlanArticleType planArticleType) {
        JSONObject data = new JSONObject();
        JSONArray arr = new JSONArray();

        JSONObject obj2 = new JSONObject();
        obj2.put("num", "№");
        obj2.put("kuu", "КУУ");
        obj2.put("kvu", "КВУ");
        switch (planArticleType) {
            case CODE_20120: {
                obj2.put("name", "Статья");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20200:{
                obj2.put("name", "Показатели");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20301:{
                obj2.put("a1", "Наименование канала связи");
                obj2.put("affilate", "Подразделение");
                obj2.put("contract", "Договор №");
                obj2.put("a2", "Процент оплаты");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                obj2.put("a4", "Стоимость аренды каналов в сутки/месяц");
                break;
            }
            case CODE_20302:{
                obj2.put("name", "Наименование аэродрома");
                obj2.put("contract", "Договор №");
                obj2.put("a3", "Остаток по договору на начало года");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20303:{
                obj2.put("affilate", "Подразделение");
                obj2.put("name2", "Наименование объекта");
                obj2.put("a5", "Кол-во потреб. эл. энергии в год кв.ч.");
                obj2.put("contractend", "Дата окончания договора");
                obj2.put("a3", "Остаток по договору на начало года");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20304:{
                obj2.put("name2", "Наименование объекта");
                obj2.put("name3", "Наименование расходов");
                obj2.put("contractcode", "Договор №");
                obj2.put("contractend", "Дата окончания договора");
                obj2.put("a3", "Остаток по договору на начало года");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20305:{
                obj2.put("affilate", "Подразделение");
                obj2.put("a6", "Наименование материала, ЗИП");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20306:{
                obj2.put("a7", "Цена 1 литра, руб.");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20307:{
                obj2.put("affilate", "Подразделение");
                obj2.put("a6", "Наименование материала, ЗИП");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20308:{
                obj2.put("name", "Наименование аэродрома");
                obj2.put("contractcode", "Договор №");
                obj2.put("contractend", "Дата окончания договора");
                obj2.put("a3", "Остаток по договору на начало года");
                obj2.put("a8", "Среднегодовой объем услуг");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20309:{
                obj2.put("a9", "Наименование медицинского учреждения");
                obj2.put("a10", "Вид медицинских услуг");
                obj2.put("a11", "Кол-во услуг в год");
                obj2.put("a12", "Средняя стоимость 1 услуги");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20310:{
                obj2.put("a13", "Наименование справочника");
                obj2.put("a14", "Кол-во экземпляров");
                obj2.put("a15", "Стоимость 1 экземпляра");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20401:{
                obj2.put("a16", "Наименование службы");
                obj2.put("a17", "Наименование учебного заведения / Место командировки");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20402:{
                obj2.put("name", "Статья");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20403:{
                obj2.put("a18", "Наименование ОПФ");
                obj2.put("contract", "Договор №");
                obj2.put("a19", "Остаток по договору на началого года");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20404:{
                obj2.put("a20", "Земельный участок");
                obj2.put("contractcode", "Договор №");
                obj2.put("contractend", "Дата окончания договора");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20405:{
                obj2.put("a16", "Наименование службы");
                obj2.put("a21", "Цель командировки");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20406:{
                obj2.put("a22", "Вид налога");
                obj2.put("a23", "Налогооблагаемая база");
                obj2.put("a24", "Ставка");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20407:{
                obj2.put("name", "Наименование мероприятия");
                obj2.put("a25", "Место проведения");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20408:{
                obj2.put("name", "Наименование расходов, объекта");
                obj2.put("a26", "Прочие расходы");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            case CODE_20500:{
                obj2.put("a27", "Наименование подразделения, объекта");
                obj2.put("contractcode", "Договор №");
                obj2.put("contractend", "Дата окончания договора");
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
            default:{
                obj2.put("q1", "Кв1");
                obj2.put("q2", "Кв2");
                obj2.put("q3", "Кв3");
                obj2.put("q4", "Кв4");
                break;
            }
        }
        final int[] i = {0};
        JSONObject finalObj = obj2;
        selected.get(0).getApplicationStageValue().getChilds().stream().forEach(row -> {
            if (i[0] == 0) {
                finalObj.put("year0", "Запланировано в пред. периоды");
            } else {
                finalObj.put("year" + i[0], row.getYear());
            }
            i[0]++;
        });

        arr.put(obj2);

        selected.stream().forEach(app -> {
            JSONObject obj = new JSONObject();

            obj.put("num", app.getOrder());

            ApplicationStageValue v = app.getApplicationStageValue();
            PlanAccount a = null;
            if (v != null) {
                a = v.getPlanAccount();
            }
            obj.put("kuu", (v == null || a == null ? "" : a.getName()));
            
            Account a2 = null;
            if (v != null) {
                a2 = v.getAnalytica();
            }
            obj.put("kvu", (v == null || a == null ? "" : a2.getName()));


            ApplicationStageContract c = app.getApplicationStageContract();
            ContractStage c2 = null;
            if (c != null) {
                c2 = c.getContract();
            }
            Affilate affilate = app.getAffiliate();



            switch (planArticleType) {
                case CODE_20120: {
                    obj.put("name", app.getName());
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20200:{
                    obj.put("name", app.getName());
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20301:{
                    obj.put("a1", "Наименование канала связи");
                    obj.put("affilate", (affilate == null ? "" : affilate.getName()));
                    obj.put("contract", (c == null || c2 == null ? "" : c2.getCode()));
                    obj.put("a2", "Процент оплаты");
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    obj.put("a4", "Стоимость аренды каналов в сутки/месяц");
                    break;
                }
                case CODE_20302:{
                    obj.put("name", app.getName());
                    obj.put("contract", (c == null || c2 == null ? "" : c2.getCode()));
                    obj.put("a3", "Остаток по договору на начало года");
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20303:{
                    obj.put("affilate", (affilate == null? "" : affilate.getName()));
                    obj.put("name2", "Наименование объекта");
                    obj.put("a5", "Кол-во потреб. эл. энергии в год кв.ч.");
                    obj.put("contractend", (c == null || c2 == null ? "" : c2.getEnd()));
                    obj.put("a3", "Остаток по договору на начало года");
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20304:{
                    obj.put("name2", "Наименование объекта");
                    obj.put("name3", "Наименование расходов");
                    obj.put("contractcode", (c == null || c2 == null ? "" : c2.getCode()));
                    obj.put("contractend", (c == null || c2 == null ? "" : c2.getEnd()));
                    obj.put("a3", "Остаток по договору на начало года");
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20305:{
                    obj.put("affilate", (affilate == null? "" : affilate.getName()));
                    obj.put("a6", "Наименование материала, ЗИП");
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20306:{
                    obj.put("a7", "Цена 1 литра, руб.");
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20307:{
                    obj.put("affilate", (affilate == null? "" : affilate.getName()));
                    obj.put("a6", "Наименование материала, ЗИП");
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20308:{
                    obj.put("name", app.getName());
                    obj.put("contractcode", (c == null || c2 == null ? "" : c2.getCode()));
                    obj.put("contractend", (c == null || c2 == null ? "" : c2.getEnd()));
                    obj.put("a3", "Остаток по договору на начало года");
                    obj.put("a8", "Среднегодовой объем услуг");
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20309:{
                    obj.put("a9", "Наименование медицинского учреждения");
                    obj.put("a10", "Вид медицинских услуг");
                    obj.put("a11", "Кол-во услуг в год");
                    obj.put("a12", "Средняя стоимость 1 услуги");
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20310:{
                    obj.put("a13", "Наименование справочника");
                    obj.put("a14", "Кол-во экземпляров");
                    obj.put("a15", "Стоимость 1 экземпляра");
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20401:{
                    obj.put("a16", "Наименование службы");
                    obj.put("a17", "Наименование учебного заведения / Место командировки");
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20402:{
                    obj.put("name", app.getName());
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20403:{
                    obj.put("a18", "Наименование ОПФ");
                    obj.put("contract", (c == null || c2 == null ? "" : c2.getCode()));
                    obj.put("a19", "Остаток по договору на началого года");
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20404:{
                    obj.put("a20", "Земельный участок");
                    obj.put("contractcode", (c == null || c2 == null ? "" : c2.getCode()));
                    obj.put("contractend", (c == null || c2 == null ? "" : c2.getEnd()));
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20405:{
                    obj.put("a16", "Наименование службы");
                    obj.put("a21", "Цель командировки");
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20406:{
                    obj.put("a22", "Вид налога");
                    obj.put("a23", "Налогооблагаемая база");
                    obj.put("a24", "Ставка");
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20407:{
                    obj.put("name", app.getName());
                    obj.put("a25", "Место проведения");
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20408:{
                    obj.put("name", app.getName());
                    obj.put("a26", "Прочие расходы");
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                case CODE_20500:{
                    obj.put("a27", "Наименование подразделения, объекта");
                    obj.put("contractcode", (c == null || c2 == null ? "" : c2.getCode()));
                    obj.put("contractend", (c == null || c2 == null ? "" : c2.getEnd()));
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
                default:{
                    obj.put("q1", app.getQ1());
                    obj.put("q2", app.getQ2());
                    obj.put("q3", app.getQ3());
                    obj.put("q4", app.getQ4());
                    break;
                }
            }

            
            
            i[0] = 0;
            app.getApplicationStageValue().getChilds().stream().forEach(year -> {
                String n2 = "year" + i[0];
                obj.put(n2, year.getValue());
                i[0]++;
            });

            arr.put(obj);
        });
        data.put("items", arr);
        return data;
    }
}
