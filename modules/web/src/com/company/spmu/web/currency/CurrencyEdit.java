package com.company.spmu.web.currency;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Currency;

@UiController("spmu_Currency.edit")
@UiDescriptor("currency-edit.xml")
@EditedEntityContainer("currencyDc")
@LoadDataBeforeShow
public class CurrencyEdit extends StandardEditor<Currency> {
}