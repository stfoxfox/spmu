package com.company.spmu.web.planarticle;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.PlanArticle;

@UiController("spmu_PlanArticle.browse")
@UiDescriptor("plan-article-browse.xml")
@LookupComponent("planArticlesTable")
@LoadDataBeforeShow
public class PlanArticleBrowse extends StandardLookup<PlanArticle> {
}