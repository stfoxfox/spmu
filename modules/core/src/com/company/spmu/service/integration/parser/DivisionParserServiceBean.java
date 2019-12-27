package com.company.spmu.service.integration.parser;

import com.company.spmu.entity.Affilate;
import com.company.spmu.entity.Division;
import com.company.spmu.repositories.entity.AffilateRepository;
import com.company.spmu.repositories.entity.DivisionRepository;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

/**
 * сервис парсинга подразделений филиалов
 */
@Service(DivisionParserService.NAME)
public class DivisionParserServiceBean implements DivisionParserService {
    @Inject
    private DivisionRepository divisionRepository;
    @Inject
    private Metadata metadata;
    @Inject
    private AffilateRepository affilateRepository;
    @Inject
    private DataManager dataManager;

    /**
     * парсинг подразделения филиала из объекта
     * @param divisionObject - объект подразделения филиала
     */
    public void parse(JSONObject divisionObject) {
        UUID divisionUuid = UUID.fromString(divisionObject.getString("Ref_Key"));
        if(divisionRepository.findByGuid(divisionUuid).isEmpty()) {
            Division division = this.metadata.create(Division.class);
            division.setCode(divisionObject.getString("Code"));
            division.setGuid(divisionUuid);
            division.setName(divisionObject.getString("Description"));
            division.setDescription(divisionObject.getString("Description"));
            if (divisionObject.getString("Parent_Key").equals("00000000-0000-0000-0000-000000000000")) {
                UUID parentUuid = UUID.fromString(divisionObject.getString("Parent_Key"));
                List<Affilate> affilates = affilateRepository.findByGuid(parentUuid);
                if (!affilates.isEmpty()) {
                    division.setAffiliate(affilates.get(0));
                }
            }
            dataManager.commit(division);
        }
    }
}