package com.company.spmu.web.applicationstageexpense.stages;

import com.company.spmu.entity.ApplicationStageExpense;
import com.company.spmu.enumeration.PlanArticleType;
import com.company.spmu.web.applicationstageexpense.ApplicationStageExpenseEdit;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.builders.EditorClassBuilder;
import com.haulmont.cuba.gui.screen.FrameOwner;
import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenFragment;

import java.util.function.Consumer;

public class ApplicationStageExpenseEditScreenFactory {


    private ScreenBuilders screenBuilders;
    private FrameOwner frameOwner;
    private Consumer afterScreenCloseEvent;

    public enum Mode {
        NEW,
        EDIT
    }

    public ApplicationStageExpenseEditScreenFactory(ScreenBuilders screenBuilders) {
        this.screenBuilders = screenBuilders;
    }

    public ApplicationStageExpenseEditScreenFactory setFrameOwner(FrameOwner frameOwner) {
        this.frameOwner = frameOwner;
        return this;
    }

    public ApplicationStageExpenseEditScreenFactory setAfterScreenCloseEvent(Consumer afterScreenCloseEvent) {
        this.afterScreenCloseEvent = afterScreenCloseEvent;
        return this;
    }

    /**
     * Builds screen, calls newEntity by default.
     *
     * @param entity entity to create
     * @return built screen
     */
    public Screen buildScreen(ApplicationStageExpense entity) {
        return buildScreen(entity, Mode.NEW);
    }

    /**
     * Builds screen, calls newEntity / editEntity depending on given mode
     *
     * @param entity entity to create / update
     * @param mode   edit screen mode
     * @return built screen
     */
    public Screen buildScreen(ApplicationStageExpense entity, Mode mode) {
        if (Mode.EDIT.equals(mode)) {
            initBuilder(entity).editEntity(entity).build();
        }

        return initBuilder(entity).newEntity(entity).build();

    }

    private EditorClassBuilder initBuilder(ApplicationStageExpense entity) {
        return screenBuilders.editor(ApplicationStageExpense.class, frameOwner)
                .withScreenClass(getScreenEditorClass(entity))
                .withLaunchMode(OpenMode.NEW_WINDOW)
                .withAfterCloseListener(afterScreenCloseEvent);
    }

    private Class getScreenEditorClass(ApplicationStageExpense entity) {
        try {
            switch (PlanArticleType.fromId(entity.getApplication().getPlanArticle().getCode())) {
                case CODE_20120:
                    return ApplicationStageExpenseArticle20120Edit.class;
                case CODE_20200:
                    return ApplicationStageExpenseArticle20200Edit.class;
                case CODE_20301:
                    return ApplicationStageExpenseArticle20301Edit.class;
                case CODE_20302:
                    return ApplicationStageExpenseArticle20302Edit.class;
                case CODE_20303:
                    return ApplicationStageExpenseArticle20303Edit.class;
                case CODE_20304:
                    return ApplicationStageExpenseArticle20304Edit.class;
                case CODE_20305:
                    return ApplicationStageExpenseArticle20305Edit.class;
                case CODE_20306:
                    return ApplicationStageExpenseArticle20306Edit.class;
                case CODE_20307:
                    return ApplicationStageExpenseArticle20307Edit.class;
                case CODE_20308:
                    return ApplicationStageExpenseArticle20308Edit.class;
                case CODE_20309:
                    return ApplicationStageExpenseArticle20309Edit.class;
                case CODE_20310:
                    return ApplicationStageExpenseArticle20310Edit.class;
                case CODE_20401:
                    return ApplicationStageExpenseArticle20401Edit.class;
                case CODE_20402:
                    return ApplicationStageExpenseArticle20402Edit.class;
                case CODE_20403:
                    return ApplicationStageExpenseArticle20403Edit.class;
                case CODE_20404:
                    return ApplicationStageExpenseArticle20404Edit.class;
                case CODE_20405:
                    return ApplicationStageExpenseArticle20405Edit.class;
                case CODE_20406:
                    return ApplicationStageExpenseArticle20406Edit.class;
                case CODE_20407:
                    return ApplicationStageExpenseArticle20407Edit.class;
                case CODE_20408:
                    return ApplicationStageExpenseArticle20408Edit.class;
                case CODE_20500:
                    return ApplicationStageExpenseArticle20500Edit.class;
                default:
                    return ApplicationStageExpenseArticleDefaultEdit.class;
            }
        } catch (Exception e) {
            return ApplicationStageExpense.class;
        }
    }
}
