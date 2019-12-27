package com.company.spmu.web.screens;

import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.web.app.loginwindow.AppLoginWindow;

public class ExtAppLoginWindow extends AppLoginWindow {
    @Subscribe("submit")
    private void onSubmit(Action.ActionPerformedEvent event) {
        this.login();
    }

}
