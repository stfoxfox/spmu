package com.company.spmu.web.currency;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Currency;

@UiController("spmu_Currency.browse")
@UiDescriptor("currency-browse.xml")
@LookupComponent("currenciesTable")
@LoadDataBeforeShow
public class CurrencyBrowse extends StandardLookup<Currency> {
}