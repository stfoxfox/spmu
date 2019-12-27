package com.company.spmu.service;

import com.company.spmu.entity.*;
import com.company.spmu.enumeration.ApplicationResponsibleRole;
import com.company.spmu.repositories.entity.ApplicationStageRepository;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.entity.Entity;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import static com.haulmont.yarg.loaders.factory.DefaultLoaderFactory.JSON_DATA_LOADER;

@Service(AppExpenseService.NAME)
public class AppExpenseServiceBean implements AppExpenseService {
    @Inject
    private Persistence persistence;
    @Inject
    private DataManager dataManager;
    @Inject
    private ReportHtmlService reportHtmlService;
    @Inject
    private ApplicationStageRepository applicationStageRepository;

    @Override
    public void saveInitiator(Application ai, Employee employee) {
        this.saveResponsible(ai, employee, ApplicationResponsibleRole.INITIATOR, ApplicationInitiator.class);
    }

    @Override
    public void saveCurator(Application ai, Employee employee) {
        this.saveResponsible(ai, employee, ApplicationResponsibleRole.CURATOR, ApplicationCurator.class);
    }

    @Override
    public void saveManager(Application ai, Employee employee) {
        this.saveResponsible(ai, employee, ApplicationResponsibleRole.MANAGER, ApplicationManager.class);
    }

    private void saveResponsible(Application ai, Employee employee, ApplicationResponsibleRole role, Class ResponsibleClass) {
        try (Transaction tx = this.persistence.createTransaction()) {
            EntityManager entityManager = this.persistence.getEntityManager();
            Query query = entityManager.createNativeQuery("select * FROM SPMU_APPLICATION_RESPONSIBLE where APPLICATION_ID=?2 and ROLE=?4",
                    ApplicationResponsible.class);
            query.setParameter(2, ai.getId());
            query.setParameter(4, role);
            List<?> list = query.getResultList();
            if (list.size() == 0) {
                Method m = ResponsibleClass.getDeclaredMethod("getNew", Application.class, Employee.class);
                m.setAccessible(true);
                Object ae = ResponsibleClass.cast(m.invoke(null, ai, employee));
                this.dataManager.commit((Entity) ae);
            } else {
                ApplicationResponsible found = null;
                for (Iterator it = list.iterator(); it.hasNext(); ) {
                    ApplicationResponsible a = (ApplicationResponsible) it.next();
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
                    Method m = ResponsibleClass.getDeclaredMethod("getNew", Application.class, Employee.class);
                    m.setAccessible(true);
                    found = (ApplicationResponsible) m.invoke(null, ai, employee);
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
    public double recalcStagesSum(Application parentApp) {
        List<ApplicationStage> list = applicationStageRepository.findByApplication(parentApp);
        return list.stream().mapToDouble(ApplicationStage::getValue).sum();
    }

    @Override
    public List<ApplicationDetailType> getAllDetailTypes() {
        Transaction tx = persistence.createTransaction();
        List<ApplicationDetailType> list;
        try {
            EntityManager em = persistence.getEntityManager();
            Query q = em.createQuery("select t from spmu_ApplicationDetail t", ApplicationDetailType.class);
            list = q.getResultList();
            tx.commit();
        } finally {
            tx.end();
        }
        return list;
    }


    @Override
    public List<ApplicationStageValue> getParentList(ApplicationStageValue applicationStageValue) {
        Transaction tx = persistence.createTransaction();
        List<ApplicationStageValue> list;
        try {
            EntityManager entityManager = this.persistence.getEntityManager();
            Query query = entityManager.createNativeQuery("select * from SPMU_APPLICATION_STAGE_VALUE  where PARENT_ID=?1 order by YEAR_", ApplicationStageValue.class);
            query.setParameter(1, applicationStageValue.getId());
            list = query.getResultList();
            tx.commit();
        } finally {
            tx.end();
        }
        return list;
    }

    @Override
    public byte[] printReport(List<ApplicationExpense> selected) throws IOException {
        JSONObject data = new JSONObject();
        JSONArray arr = new JSONArray();
        selected.stream().forEach(app -> {
            JSONObject obj = new JSONObject();
            obj.put("name", app.getName());
            obj.put("code", app.getCode());
            Affilate af = app.getAffiliate();
            obj.put("affiliate", (af == null ? "" : af.getName()));
            PlanArticle p = app.getPlanArticle();
            obj.put("vpo", (p == null ? "" : p.getName()));
            DateFormat f = new SimpleDateFormat("dd.MM.yyyy");
            obj.put("datestart", f.format(app.getDateStart()));
            obj.put("dateend", f.format(app.getDateEnd()));
            obj.put("price", app.getValue());
            arr.put(obj);
        });
        data.put("items", arr);

        ByteArrayOutputStream outputStream = this.reportHtmlService.getPrintReport("appExpense.html", data);
        return outputStream.toByteArray();
    }

    @Override
    public ApplicationExpense getExpenseWithParams(Integer year, PlanArticle planArticle, Division division) {
        ApplicationExpense found;
        try (Transaction tx = persistence.createTransaction()) {
            try {
                EntityManager entityManager = this.persistence.getEntityManager();
                Query query = entityManager.createQuery("SELECT e FROM spmu_ApplicationExpense e WHERE e.year=:y AND e.planArticle=:a AND e.division=:d", ApplicationExpense.class);
                query.addView(ApplicationExpense.class, "applicationExpense-view-create");
                query.setParameter("a", planArticle);
                query.setParameter("y", year);
                query.setParameter("d", division);
                found = (ApplicationExpense) query.getSingleResult();
                tx.commit();
            } finally {
                tx.end();
            }
        }
        catch (Exception e) {
            found = null;
        }
        return found;
    }

    @Override
    public void removeStages(ApplicationExpense parent) {
        List<ApplicationStage> list = applicationStageRepository.findByApplication(parent);
        list.forEach(applicationStageExpense -> this.dataManager.remove(applicationStageExpense));
    }

    @Override
    public List<PlanArticleStage> getAllPlanArticleStages(PlanArticle planArticle) {
        List<PlanArticleStage> list;
        try (Transaction tx = persistence.createTransaction()) {
            try {
                EntityManager em = persistence.getEntityManager();
                Query q = em.createQuery("select t from spmu_PlanArticleStage t WHERE t.planArticle=?1", PlanArticleStage.class);
                q.setParameter(1, planArticle);
                list = q.getResultList();
                tx.commit();
            } finally {
                tx.end();
            }
        }
        return list;
    }

    @Override
    public boolean isStageCreatedFromPlanCanEdited(ApplicationStageExpense edit) {
        try (Transaction tx = persistence.createTransaction()) {
            try {
                EntityManager em = persistence.getEntityManager();
                Query q = em.createQuery("select t from spmu_PlanArticleStage t WHERE t.planArticle=?1 AND t.name=?2", PlanArticleStage.class);
                q.setParameter(1, edit.getPlanArticle());
                q.setParameter(2, edit.getName());
                PlanArticleStage plan = (PlanArticleStage) q.getSingleResult();
                if (plan == null) return true;
                tx.end();
                return plan.getIsChange();
            }
            catch (Exception e) {
                System.out.println(e);
            }
            finally {
                tx.end();
            }
        }
        catch (Exception e) {}
        return true;
    }

    /**
     * Формируем json объект с массивом данных для экспорта
     * @param selected
     * @return
     */
    private JSONObject getDataForExport(List<ApplicationExpense> selected) {
        JSONObject data = new JSONObject();
        JSONArray arr = new JSONArray();
        selected.stream().forEach(app -> {
            JSONObject obj = new JSONObject();
            obj.put("name", app.getName());
            obj.put("code", app.getCode());
            Affilate af = app.getAffiliate();
            obj.put("affiliate", (af == null ? "" : af.getName()));
            PlanArticle p = app.getPlanArticle();
            obj.put("vpo", (p == null ? "" : p.getName()));
            DateFormat f = new SimpleDateFormat("dd.MM.yyyy");
            obj.put("datestart", f.format(app.getDateStart()));
            obj.put("dateend", f.format(app.getDateEnd()));
            obj.put("price", app.getValue());
            arr.put(obj);
        });
        data.put("items", arr);
        return data;
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

    @Override
    public byte[] exportReport(List<ApplicationExpense> selected, ReportOutputType outputType) throws IOException {
        JSONObject data = this.getDataForExport(selected);
        ByteArrayOutputStream outputStream = this.getReport("appexpenses.docx", data, outputType);
        return outputStream.toByteArray();
    }

}
