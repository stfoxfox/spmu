package com.company.spmu.web;

import com.haulmont.cuba.gui.screen.StandardCloseAction;

/**
 * Передача выбранного значения селектора в родительское окно
 */
public class RequestNewCloseAction extends StandardCloseAction {
    public Integer selected;

    public RequestNewCloseAction(Integer selected) {
        super("RequestNew");
        this.selected = selected;
    }
}
