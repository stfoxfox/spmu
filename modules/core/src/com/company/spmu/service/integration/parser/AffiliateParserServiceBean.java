package com.company.spmu.service.integration.parser;

import com.company.spmu.service.integration.IntegrationService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import com.company.spmu.entity.Affilate;
import com.company.spmu.repositories.entity.AffilateRepository;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

/**
 * сервис парсинга филиалов
 */
@Service(AffiliateParserService.NAME)
public class AffiliateParserServiceBean implements AffiliateParserService {
    @Inject
    private IntegrationService integrationService;
    @Inject
    private Metadata metadata;
    @Inject
    private Persistence persistence;
    @Inject
    private DataManager dataManager;
    @Inject
    private AffilateRepository affilateRepository;

    /**
     * парсинг филиала из объекта
     * @param affiliateObject - объект филиала
     */
    public void parse(JSONObject affiliateObject) {
        try (Transaction tx = this.persistence.createTransaction()) {
            UUID affiliateUuid = UUID.fromString(affiliateObject.getString("Ref_Key"));
            if (affilateRepository.findByGuid(affiliateUuid).isEmpty()) {
                Affilate affilate = this.metadata.create(Affilate.class);
                affilate.setGuid(UUID.fromString(affiliateObject.getString("Ref_Key")));
                affilate.setCode(affiliateObject.getString("Code"));
                affilate.setDescription(affiliateObject.getString("Description"));
                affilate.setName(affiliateObject.getString("Description"));
                if (affiliateObject.getString("Parent_Key").equals("00000000-0000-0000-0000-000000000000")) {
                    UUID parentUuid = UUID.fromString(affiliateObject.getString("Parent_Key"));
                    List<Affilate> parentAffilate = affilateRepository.findByGuid(parentUuid);
                    if (!parentAffilate.isEmpty()) {
                        affilate.setParent(parentAffilate.get(0));
                    }
                }
                dataManager.commit(affilate);
            }

            tx.commit();
        }
    }



}