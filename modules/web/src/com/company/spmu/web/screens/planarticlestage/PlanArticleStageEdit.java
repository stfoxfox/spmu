package com.company.spmu.web.screens.planarticlestage;

import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.PlanArticleStage;

@UiController("spmu_PlanArticleStage.edit")
@UiDescriptor("plan-article-stage-edit.xml")
@EditedEntityContainer("planArticleStageDc")
@LoadDataBeforeShow
public class PlanArticleStageEdit extends StandardEditor<PlanArticleStage> {
}