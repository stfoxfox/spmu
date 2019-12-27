package com.company.spmu.repositories.entity;

import com.company.spmu.entity.Application;
import com.company.spmu.entity.Registry;
import com.company.spmu.entity.RegistryContent;
import com.company.spmu.entity.RegistryContentPk;
import com.haulmont.addons.cuba.jpa.repositories.config.CubaJpaRepository;
import com.haulmont.addons.cuba.jpa.repositories.config.CubaView;
import com.haulmont.addons.cuba.jpa.repositories.config.JpqlQuery;

import java.util.List;
import java.util.UUID;

public interface RegistryContentRepository extends CubaJpaRepository<RegistryContent, UUID> {

    List<RegistryContent> findByRegistryAndApplication(Registry registry, Application application);
}
