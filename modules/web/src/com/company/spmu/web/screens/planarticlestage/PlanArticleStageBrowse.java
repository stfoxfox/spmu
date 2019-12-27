package com.company.spmu.web.screens.planarticlestage;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.PlanArticleStage;

@UiController("spmu_PlanArticleStage.browse")
@UiDescriptor("plan-article-stage-browse.xml")
@LookupComponent("planArticleStagesTable")
@LoadDataBeforeShow
public class PlanArticleStageBrowse extends StandardLookup<PlanArticleStage> {
}