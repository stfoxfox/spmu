package com.company.spmu.custom;

import com.haulmont.cuba.core.global.ViewRepository;

public interface CustomViewRepository extends ViewRepository {
    void createAndStoreView(Class className, String name);
}
