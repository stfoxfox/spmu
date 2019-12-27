package com.company.spmu.repositories.entity;

import com.company.spmu.entity.Affilate;
import com.company.spmu.entity.Division;
import com.haulmont.addons.cuba.jpa.repositories.config.CubaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DivisionRepository extends CubaJpaRepository<Division, UUID> {
    List<Division> findByGuid(UUID guid);
}
