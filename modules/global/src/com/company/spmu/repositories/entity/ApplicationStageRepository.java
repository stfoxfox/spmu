package com.company.spmu.repositories.entity;

import com.company.spmu.entity.Application;
import com.company.spmu.entity.ApplicationStage;
import com.haulmont.addons.cuba.jpa.repositories.config.CubaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationStageRepository extends CubaJpaRepository<ApplicationStage, UUID>{
    @Override
    ApplicationStage findOne(UUID uuid);

    @Override
    List<ApplicationStage> findAll();

    List<ApplicationStage> findByApplication(Application application);

}
