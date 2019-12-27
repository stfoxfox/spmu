package com.company.spmu.service.integration.parser;

import com.company.spmu.entity.Affilate;
import com.company.spmu.entity.ApplicationEvent;
import com.company.spmu.entity.SpmuUser;
import com.company.spmu.repositories.entity.AffilateRepository;
import com.company.spmu.repositories.entity.ApplicationEventRepository;
import com.company.spmu.service.integration.IntegrationService;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.UserSessionSource;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * сервис парсинга заявок на мероприятие
 */
@Service(ApplicationEventParserService.NAME)
public class ApplicationEventParserParserServiceBean implements ApplicationEventParserService {
    @Inject
    private IntegrationService integrationService;
    @Inject
    private ApplicationEventRepository applicationEventRepository;
    @Inject
    private Metadata metadata;
    @Inject
    private Persistence persistence;
    @Inject
    private AffilateRepository affilateRepository;
    @Inject
    private UserSessionSource userSessionSource;
    @Inject
    private DataManager dataManager;

    /** парсинг заявок на мероприятие json объекта object
     * @param object
     * @return
     */
    public ApplicationEvent parse(JSONObject object) {
        ApplicationEvent applicationEvent = this.metadata.create(ApplicationEvent.class);

        String applicationEventUuid = object.getString("ЗаявкаСсылка");
        String applicationEventUrl = "odata/standard.odata/Document_ОРВД_ЗаявкаНаМероприятия_Изолированная(guid:'".concat(applicationEventUuid).concat("')?$format=json");

        String response = integrationService.getData(applicationEventUrl);
        if (response.isEmpty()) {
            return null;
        }

        JSONObject applicationEventObject = new JSONObject(response);

        try (Transaction tx = this.persistence.createTransaction()) {

            List<ApplicationEvent> applicationEventList = applicationEventRepository.findByCode(applicationEventObject.getString("НомерЗаявкиОригинал"));

            if (!applicationEventList.isEmpty()) {
                applicationEvent = applicationEventList.get(0);
            } else {
                applicationEvent.setUuid(UUID.fromString(applicationEventUuid));
            }

            applicationEvent.setCode(applicationEventObject.getString("НомерЗаявкиОригинал"));
            applicationEvent.setYear(applicationEventObject.getInt("ОРВД_ГодИнициацииМероприятия"));

            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(applicationEventObject.getString("Date"));
                Date dateStart = format.parse(applicationEventObject.getString("ОРВД_ДатаНачала"));
                Date dateEnd = format.parse(applicationEventObject.getString("ОРВД_ДатаОкончания"));

                applicationEvent.setDate(date);
                applicationEvent.setDateStart(dateStart);
                applicationEvent.setDateEnd(dateEnd);
            } catch(ParseException e) {
                System.out.println(e.getMessage());
            }


            UUID affiliateGuid = UUID.fromString(applicationEventObject.getString("ОРВД_Филиал_Key"));
            List<Affilate> affilateList = affilateRepository.findByGuid(affiliateGuid);
            if (!affilateList.isEmpty()) {
                applicationEvent.setAffiliate(affilateList.get(0));
            }

            applicationEvent.setName(applicationEventObject.getString("НаименованиеМероприятия"));
            applicationEvent.setIsGge(applicationEventObject.getBoolean("НаличиеГГЭ"));
            applicationEvent.setIsPsd(applicationEventObject.getBoolean("НаличиеПСД"));

            SpmuUser user = (SpmuUser) this.userSessionSource.getUserSession().getUser();
            if (user != null) {
                applicationEvent.setResponsible(user.getEmployee());
            }

            dataManager.commit(applicationEvent);

            tx.commit();

            return applicationEvent;
        }
    }
}