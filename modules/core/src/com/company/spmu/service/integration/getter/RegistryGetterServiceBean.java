package com.company.spmu.service.integration.getter;

import com.company.spmu.service.integration.IntegrationService;
import com.company.spmu.service.integration.parser.RegistryParserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service(RegistryGetterService.NAME)
public class RegistryGetterServiceBean implements RegistryGetterService {
    @Inject
    private IntegrationService integrationService;
    @Inject
    private RegistryParserService registryParserService;

    private static final String ADDITIONAL_URL = "hs/RegisterRequests/";

    /**
     * получение списка реестров
     */
    @Override
    public void getData() {
        String response = integrationService.getData(ADDITIONAL_URL.concat("0")).replaceAll("\\p{C}", "");

        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("Результат");

        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            registryParserService.parse(object);
        }
    }
}