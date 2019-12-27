
package com.company.spmu.repositories.entity;

import com.company.spmu.entity.Application;
import com.company.spmu.entity.ApplicationEditor;
import com.haulmont.addons.cuba.jpa.repositories.config.CubaJpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationEditorRepository  extends CubaJpaRepository<ApplicationEditor, UUID> {
    List<ApplicationEditor> findByUuid(UUID uuid);

    List<ApplicationEditor> findByApplication(Application application);
}
