package com.company.spmu.service.integration.export;

import com.company.spmu.entity.*;
import com.company.spmu.repositories.entity.MessageLogRepository;
import com.company.spmu.repositories.entity.StatusLinkRepository;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.*;
import net.minidev.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * сервис формирования сообщения
 * для отпрвки данных по заявке на мероприятие
 * в УИМ внутренний
 */
@Service(ApplicationEventExportService.NAME)
public class ApplicationEventExportServiceBean implements ApplicationEventExportService {
    @Inject
    private MessageLogRepository messageLogRepository;
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;
    @Inject
    private DataManager dataManager;
    @Inject
    private UserSessionSource userSessionSource;
    @Inject
    private Messages messages;
    @Inject
    private StatusLinkRepository statusLinkRepository;

    /**
     * Получение JSON строки сообщения реестра
     * для отправки статуса заявки в УИМ внут
     * @param application - заявка
     * @return
     */
    @Override
    public String getExportJsonString(Application application) {
        try(Transaction tx = this.persistence.getTransaction()) {
            MessageLog messageLog = this.metadata.create(MessageLog.class);
            dataManager.commit(messageLog);
            tx.commit();
        }

        long numberMessage = MessageLog.LAST_RECORD_IN_UIM + messageLogRepository.count();
        Employee employee = ((SpmuUser) userSessionSource.getUserSession().getUser()).getEmployee();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String date = format.format(new Date());

        String status = messages.getMessage(application.getStatus());
        List<StatusLink> statusLinkList = statusLinkRepository.findByStatus(application.getStatus().getId());
        if (!statusLinkList.isEmpty()) {
            status = statusLinkList.get(0).getCode();
        }

        JSONObject applicationEventObject = new JSONObject();
        applicationEventObject.put("ОписьРеестра", new JSONObject()
                        .put("Номер", numberMessage)
                        .put("КоличествоСообщений", 1)
                        .put("ДатаОтправки", date));

        JSONArray applicationArray = new JSONArray();
        JSONObject applicationItem = new JSONObject();
        applicationItem.put("ТипЗаявки", "ОРВД_ЗаявкаНаМероприятия_Изолированная")
                .put("ВключеноВОпцион", 0)
                .put("Статус", status)
                .put("Примечание", "")
                .put("ИдЗаявки", application.getUuid())
                .put("ФИОРедактор", employee.getSecondname())
                .put("ДолжностьРедактор", "");


        JSONArray stageArray = new JSONArray();
        JSONObject stageItem = new JSONObject();
        stageItem.put("ИДЭтапа", "ГуидСтрокиЭтапа")
                .put("План", "40aebc66-8378-11e9-80c5-005056847a3c")
                .put("Раздел", "312163b5-3397-11e9-8273-fcaa148a75a9")
                .put("Подраздел", "312163b9-3397-11e9-8273-fcaa148a75a9")
                .put("ВключенаВОпцион", 0);

        stageArray.add(stageItem);
        applicationItem.put("Этап", stageArray);
        applicationArray.add(applicationItem);
        applicationEventObject.put("Реестр", applicationArray);
        System.out.println(applicationEventObject.toString());
        return applicationEventObject.toString();
    }
}