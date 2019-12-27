package com.company.spmu.repositories.bpm;

import com.haulmont.addons.cuba.jpa.repositories.config.CubaJpaRepository;
import com.haulmont.bpm.entity.ProcModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProcModelRepository extends CubaJpaRepository<ProcModel, UUID> {
    List<ProcModel> findByName(String name);
}
