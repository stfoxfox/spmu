package com.company.spmu.repositories.entity;

import com.company.spmu.entity.SpmuUser;
import com.haulmont.addons.cuba.jpa.repositories.config.CubaJpaRepository;

import java.util.UUID;

public interface SpmuUserRepository extends CubaJpaRepository<SpmuUser, UUID> {

}
