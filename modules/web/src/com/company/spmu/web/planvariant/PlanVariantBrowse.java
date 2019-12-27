package com.company.spmu.web.planvariant;


import com.company.spmu.entity.PlanVariantLimit;
import com.company.spmu.enumeration.PlanVariantStatus;
import com.company.spmu.web.bmp.fragment.procactionsfragment.ProcActionsFragment;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.spmu.entity.PlanVariant;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@UiController("spmu_PlanVariant.browse")
@UiDescriptor("plan-variant-browse.xml")
@LookupComponent("planVariantsTable")
@LoadDataBeforeShow
public class PlanVariantBrowse extends StandardLookup<PlanVariant> {
    protected static final String PROCESS_CODE = "planvariantprocessmodel";
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private CollectionLoader<PlanVariant> planVariantsDl;
    @Inject
    private DataGrid<PlanVariant> planVariantsTable;
    @Inject
    private DataManager dataManager;
    @Inject
    private ProcActionsFragment procActionsFragment;

    @Subscribe("closeBtn")
    private void onCloseBtnClick(Button.ClickEvent event) {
        this.closeWithDefaultAction();
    }

    @Subscribe("planVariantsTable.edit")
    private void onPlanVariantsTableEdit(Action.ActionPerformedEvent event) {
        PlanVariant selected = this.dataManager.reload(this.planVariantsTable.getSingleSelected(), "planVariant.edit");
        this.screenBuilders.editor(PlanVariant.class, this)
                .editEntity(selected)
                .withLaunchMode(OpenMode.NEW_WINDOW)
                .withScreenClass(PlanVariantEdit.class)
                .withAfterCloseListener(planVariantEditAfterScreenCloseEvent -> {
                    this.planVariantsDl.load();
                })
                .build()
                .show();
    }
    @Subscribe("planVariantsTable")
    private void onRecordSelect(DataGrid.SelectionEvent<PlanVariant> event) {
        Set<PlanVariant> selected = event.getSelected();
        boolean isShowProcActions = selected.size() == 1;
        if (isShowProcActions) {
            selected.forEach(selectedEntity -> procActionsFragment.initializer()
                    .standard()
                    .init(PROCESS_CODE, selectedEntity));

        }
        procActionsFragment.getFragment().setVisible(isShowProcActions);
    }

    @Subscribe
    private void onInit(InitEvent event) {
        this.planVariantsTable.addGeneratedColumn("limityear", this.columnLimitYear());
        this.planVariantsTable.addGeneratedColumn("limitnext", this.columnLimitNextYears());
        this.planVariantsTable.addRowStyleProvider(planVariantsTable -> {
            if (planVariantsTable == null) return null;
            if (planVariantsTable.getStatus() == null) return null;
            switch (planVariantsTable.getStatus()) {
                case FINALLY_REJECTED_RA:
                    return "finally_rejected_ra";

                case DOWNLOAD_IN_YMIP:
                    return "download_in_ymip";

                case REJECTED_RA_WITH_A_NOTE_FOR_LATER_ADJUSTMENT:
                    return "rejected_ra_with_a_note_for_later_adjustment";


            }
            return null;
        });
    }

    /**
     * Колонка Лимит финансирования на год
     * @return
     */
    private DataGrid.ColumnGenerator<PlanVariant, String> columnLimitYear() {
        return new DataGrid.ColumnGenerator<PlanVariant, String>() {
            @Override
            public String getValue(DataGrid.ColumnGeneratorEvent<PlanVariant> event) {
                try {
                    return String.valueOf(event.getItem().getLimits().get(0).getValue());
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    return "";
                }
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }
        };
    }

    /**
     * Лимит финансирования на последующие годы
     * @return
     */
    private DataGrid.ColumnGenerator<PlanVariant, String> columnLimitNextYears() {
        return new DataGrid.ColumnGenerator<PlanVariant, String>() {
            @Override
            public String getValue(DataGrid.ColumnGeneratorEvent<PlanVariant> event) {
                try {
                    Double sum = 0.0;
                    List<PlanVariantLimit> list = event.getItem().getLimits();
                    for (int i = 1; i < list.size(); i++) {
                        sum += list.get(i).getValue();
                    }
                    return String.valueOf(sum);
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    return "";
                }
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }
        };
    }

}
