package com.company.spmu.repositories.entity;

import com.company.spmu.entity.ApplicationStage;
import com.company.spmu.entity.ApplicationStageValue;
import com.haulmont.addons.cuba.jpa.repositories.config.CubaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationStageValueRepository extends CubaJpaRepository<ApplicationStageValue, UUID>{

    List<ApplicationStageValue> findByParent(ApplicationStageValue parent);
    List<ApplicationStageValue> findByApplicationStage(ApplicationStage applicationStage);
}
