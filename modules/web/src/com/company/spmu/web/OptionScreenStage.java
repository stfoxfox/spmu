package com.company.spmu.web;

import com.company.spmu.entity.Application;
import com.haulmont.cuba.gui.screen.ScreenOptions;

/**
 * Для передачи в экран параметра Application
 */
public class OptionScreenStage implements ScreenOptions {
    private Application app;

    public OptionScreenStage(Application app) {
        this.app = app;
    }

    public Application getApplication() {
        return this.app;
    }
}
