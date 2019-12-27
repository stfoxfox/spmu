package com.company.spmu.service.integration.getter;

import com.company.spmu.service.integration.IntegrationService;
import com.company.spmu.service.integration.parser.AffiliateParserService;
import com.company.spmu.service.integration.parser.DivisionParserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service(AffiliateGetterService.NAME)
public class AffiliateGetterServiceBean implements AffiliateGetterService {
    @Inject
    private IntegrationService integrationService;
    @Inject
    private AffiliateParserService affiliateParserService;
    @Inject
    private DivisionParserService divisionParserService;

    private static final String ADDITIONAL_URL = "odata/standard.odata/Catalog_ат_СтруктурныеПодразделенияГК?$format=json";

    /**
     * парсинг списка филиалов и подразделений
     * получет json строку из УИМ
     * если параметр Code состоит из 3-х символов - это подразделение
     * если параметр Code состоит из 4-х символов - это филиал
     */
    public void getData() {
        String response = integrationService.getData(ADDITIONAL_URL);
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("value");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            if (object.getString("Code").length() == 3) {
                divisionParserService.parse(object);
            } else if (object.getString("Code").length() == 4) {
                affiliateParserService.parse(object);
            }
        }
    }
}