package com.company.spmu.repositories.bpm;

import com.haulmont.addons.cuba.jpa.repositories.config.CubaJpaRepository;
import com.haulmont.bpm.entity.ProcDefinition;
import com.haulmont.bpm.entity.ProcModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ProcDefinitionsRepository extends CubaJpaRepository<ProcDefinition, UUID> {
    List<ProcDefinition> findProcDefinitionsByModel(ProcModel model);
}
