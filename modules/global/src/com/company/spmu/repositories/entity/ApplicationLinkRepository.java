package com.company.spmu.repositories.entity;

import com.company.spmu.entity.Application;
import com.company.spmu.entity.ApplicationLink;
import com.company.spmu.entity.ApplicationStage;
import com.haulmont.addons.cuba.jpa.repositories.config.CubaJpaRepository;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationLinkRepository extends CubaJpaRepository<ApplicationLink, UUID>{

    List<ApplicationLink> findByParentStage(ApplicationStage parentStage);

}
