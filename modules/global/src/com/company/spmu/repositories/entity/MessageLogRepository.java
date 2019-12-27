package com.company.spmu.repositories.entity;

import com.company.spmu.entity.Affilate;
import com.company.spmu.entity.MessageLog;
import com.haulmont.addons.cuba.jpa.repositories.config.CubaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageLogRepository extends CubaJpaRepository<MessageLog, UUID> {

}
