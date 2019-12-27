package com.company.spmu.repositories.entity;

import com.company.spmu.entity.ApplicationEvent;
import com.haulmont.addons.cuba.jpa.repositories.config.CubaJpaRepository;
import com.haulmont.addons.cuba.jpa.repositories.config.CubaView;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationEventRepository extends CubaJpaRepository<ApplicationEvent, UUID> {
    List<ApplicationEvent> findByUuid(UUID uuid);

    @Override
    ApplicationEvent findOne(UUID uuid);

    @CubaView("applicationEvent-view-full")
    List<ApplicationEvent> findByCode(String code);

    @CubaView("applicationEvent-view-full")
    List<ApplicationEvent> findOneById(UUID id);

    @Override
    List<ApplicationEvent> findAll();
}
