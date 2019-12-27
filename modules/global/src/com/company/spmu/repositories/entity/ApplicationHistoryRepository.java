package com.company.spmu.repositories.entity;

import com.company.spmu.entity.bpm.ApplicationHistory;
import com.haulmont.addons.cuba.jpa.repositories.config.CubaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicationHistoryRepository extends CubaJpaRepository<ApplicationHistory, UUID> {
}
