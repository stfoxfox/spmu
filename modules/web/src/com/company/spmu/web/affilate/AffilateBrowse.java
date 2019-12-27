package com.company.spmu.web.affilate;

import com.company.spmu.service.integration.getter.AffiliateGetterService;
import com.company.spmu.service.integration.parser.AffiliateParserService;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.Affilate;

import javax.inject.Inject;

@UiController("spmu_Affilate.browse")
@UiDescriptor("affilate-browse.xml")
@LookupComponent("affilatesTable")
@LoadDataBeforeShow
public class AffilateBrowse extends StandardLookup<Affilate> {
    @Inject
    private Notifications notifications;
    @Inject
    private GroupTable<Affilate> affilatesTable;
    @Inject
    private AffiliateGetterService affiliateGetterService;


    @Subscribe
    public void parseAffiliates() {
        affiliateGetterService.getData();
        notifications.create().withCaption("Справочник филиалов обновлен").show();
        affilatesTable.refresh();
    }
}