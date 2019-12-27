package com.company.spmu.web.planarticle;

import com.company.spmu.web.custom.editors.UuidStandardEditor;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.PlanArticle;

@UiController("spmu_PlanArticle.edit")
@UiDescriptor("plan-article-edit.xml")
@EditedEntityContainer("planArticleDc")
@LoadDataBeforeShow
public class PlanArticleEdit extends UuidStandardEditor<PlanArticle> {
}