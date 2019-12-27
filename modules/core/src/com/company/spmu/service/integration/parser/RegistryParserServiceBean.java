package com.company.spmu.service.integration.parser;

import com.company.spmu.entity.ApplicationEvent;
import com.company.spmu.entity.Registry;
import com.company.spmu.entity.RegistryContent;
import com.company.spmu.repositories.entity.RegistryContentRepository;
import com.company.spmu.repositories.entity.RegistryRepository;
import com.company.spmu.service.integration.IntegrationService;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * сервис парсинга реестра заявок
 */
@Service(RegistryParserService.NAME)
public class RegistryParserServiceBean implements RegistryParserService {
    @Inject
    private IntegrationService integrationService;
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;
    @Inject
    private DataManager dataManager;
    @Inject
    private RegistryRepository registryRepository;
    @Inject
    private ApplicationEventParserService applicationEventService;
    @Inject
    private RegistryContentRepository registryContentRepository;

    private static final String ADDITIONAL_URL = "hs/RegisterRequests/";

    /**
     * парсинг объекта реестра
     * @param registryObject
     */
    public void parse(JSONObject registryObject) {
        try (Transaction tx = this.persistence.createTransaction()) {
            String registryUuid = registryObject.getString("ИдРеестрЗаявок");
            String registryUrl = "odata/standard.odata/Document_РеестрЗаявок(guid'".concat(registryUuid).concat("')?$format=json");
            String response = integrationService.getData(registryUrl);
            JSONObject jsonObject = new JSONObject(response);

            Registry registry = this.metadata.create(Registry.class);
            List<Registry> registryList = registryRepository.findByCode(jsonObject.getString("Number"));
            if (!registryList.isEmpty()) {
                registry = registryList.get(0);
            }

            registry.setUuid(UUID.fromString(registryUuid));
            registry.setCode(jsonObject.getString("Number"));

            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(jsonObject.getString("Date"));
                registry.setDate(date);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }


            dataManager.commit(registry);

            JSONArray applicationEventArray = jsonObject.getJSONArray("ВключенныеЗаявки");
            for (int i = 0; i < applicationEventArray.length(); i++) {
                JSONObject applicationEventObject = applicationEventArray.getJSONObject(i);

                ApplicationEvent applicationEvent = applicationEventService.parse(applicationEventObject);
                if (applicationEvent == null) {
                    continue;
                }

                List<RegistryContent> registryContentList = registryContentRepository.findByRegistryAndApplication(registry, applicationEvent);
                if (registryContentList.isEmpty()) {

                    RegistryContent registryContent = this.metadata.create(RegistryContent.class);
                    registryContent.setApplication(applicationEvent);
                    registryContent.setRegistry(registry);
                    dataManager.commit(registryContent);
                }
            }

            tx.commit();
        }
    }

    /**
     * создание нового реестра в УИМ внутренний
     */
    @Override
    public void createRegistry() {
        long countRegistry = registryRepository.count();
        integrationService.getData(ADDITIONAL_URL.concat(Long.toString(countRegistry)));
    }
}