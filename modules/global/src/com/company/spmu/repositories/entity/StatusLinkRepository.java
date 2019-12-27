package com.company.spmu.repositories.entity;

import com.company.spmu.entity.MessageLog;
import com.company.spmu.entity.StatusLink;
import com.haulmont.addons.cuba.jpa.repositories.config.CubaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StatusLinkRepository extends CubaJpaRepository<StatusLink, UUID> {
    List<StatusLink> findByStatus(String status);
}
