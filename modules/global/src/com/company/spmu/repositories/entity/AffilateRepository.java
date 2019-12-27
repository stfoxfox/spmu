package com.company.spmu.repositories.entity;

import com.company.spmu.entity.Affilate;
import com.haulmont.addons.cuba.jpa.repositories.config.CubaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AffilateRepository extends CubaJpaRepository<Affilate, UUID> {
    List<Affilate> findByGuid(UUID guid);
}
