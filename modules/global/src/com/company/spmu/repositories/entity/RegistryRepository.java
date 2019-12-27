package com.company.spmu.repositories.entity;

import com.company.spmu.entity.ApplicationEvent;
import com.company.spmu.entity.Registry;
import com.haulmont.addons.cuba.jpa.repositories.config.CubaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegistryRepository extends CubaJpaRepository<Registry, UUID> {
    List<Registry> findByUuid(UUID uuid);
    List<Registry> findByCode(String code);
}
