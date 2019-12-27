package com.company.spmu.web;

import com.haulmont.cuba.gui.components.Field;
import com.haulmont.cuba.gui.components.ValidationException;

import javax.annotation.Nullable;

public class YearValidator implements Field.Validator {
    @Override
    public void validate(@Nullable Object value) throws ValidationException {
        if (value == null || value.toString().length() != 4 || Integer.parseInt(value.toString()) < 2000 || Integer.parseInt(value.toString()) > 2100) {
            throw new ValidationException("Неправильно задан год");
        }
    }
}
