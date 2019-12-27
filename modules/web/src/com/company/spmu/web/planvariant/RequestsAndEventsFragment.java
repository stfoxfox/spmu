package com.company.spmu.web.planvariant;

import com.company.spmu.entity.*;
import com.company.spmu.enumeration.ApplicationStatus;
import com.company.spmu.web.RequestNewCloseAction;
import com.company.spmu.web.applicationevent.ApplicationEventEdit;
import com.company.spmu.web.applicationexpense.ApplicationExpenseEdit;
import com.company.spmu.web.applicationincome.ApplicationIncomeEdit;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.util.*;

@UiController("spmu_RequestsAndEventsFragment")
@UiDescriptor("Requests-and-events-fragment.xml")
@LoadDataBeforeShow
public class RequestsAndEventsFragment extends ScreenFragment {
    @Inject
    private CollectionLoader<PlanVariantTemplateApplication> planVariantTemplateApplicationsDl;
    @Inject
    private CollectionLoader<PlanVariantTemplateApplication> planVariantTemplateApplications2Dl;
    @Inject
    private DataGrid<PlanVariantTemplateApplication> includedTable;
    @Inject
    private DataGrid<PlanVariantTemplateApplication> excludedTable;
    @Inject
    private DataManager dataManager;
    @Inject
    private Filter includedFilter;
    @Inject
    private Filter excludedFilter;
    @Inject
    private CheckBoxGroup columnsIncludedList;
    @Inject
    private PopupView columnsIncludedPopup;
    @Inject
    private PopupView columnsExcludedPopup;
    @Inject
    private CheckBoxGroup columnsExcludedList;
    @Inject
    private TextField<String> searchIncludedField;
    @Inject
    private TextField<String> searchExcludedField;
    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe
    private void onInit(InitEvent event) {
        this.includedTable.addGeneratedColumn("typeColumn", this.typeColumn());
        this.excludedTable.addGeneratedColumn("typeColumn", this.typeColumn());

        this.initColumnsMenu();

        this.planVariantTemplateApplicationsDl.load();
        this.planVariantTemplateApplications2Dl.load();
    }

    private DataGrid.ColumnGenerator<PlanVariantTemplateApplication, String> typeColumn() {
        return new DataGrid.ColumnGenerator<PlanVariantTemplateApplication, String>() {
            @Override
            public String getValue(DataGrid.ColumnGeneratorEvent<PlanVariantTemplateApplication> event) {
                if (event.getItem().getApplication() != null) {
                    if (event.getItem().getApplication() instanceof ApplicationIncome) {
                        return "ЗД";
                    } else if (event.getItem().getApplication() instanceof ApplicationExpense) {
                        return "ЗТР";
                    } else if (event.getItem().getApplication() instanceof ApplicationEvent) {
                        if (event.getItem().getApplication().getStatus().equals(ApplicationStatus.PROJECT)) {
                            return "ПЗМ";
                        } else {
                            return "ЗЭМ";
                        }
                    } else {
                        return "";
                    }
                }
                return "";
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }
        };
    }

    @Subscribe("excludedTable.include")
    private void onExcludedTableInclude(Action.ActionPerformedEvent event) {
        Set<PlanVariantTemplateApplication> row = this.excludedTable.getSelected();
        row.forEach(planVariantTemplateApplication -> {
            planVariantTemplateApplication.setInPlan(true);
            this.dataManager.commit(planVariantTemplateApplication);
        });
        this.planVariantTemplateApplicationsDl.load();
        this.planVariantTemplateApplications2Dl.load();
    }

    @Subscribe("includedTable.exclude")
    private void onIncludedTableExclude(Action.ActionPerformedEvent event) {
        Set<PlanVariantTemplateApplication> row = this.includedTable.getSelected();
        row.forEach(planVariantTemplateApplication -> {
            planVariantTemplateApplication.setInPlan(false);
            this.dataManager.commit(planVariantTemplateApplication);
        });
        this.planVariantTemplateApplicationsDl.load();
        this.planVariantTemplateApplications2Dl.load();
    }

    @Subscribe("settingsIncludedBtn.filterIncludedAction")
    private void onSettingsRequestBtnFilterRequestAction(Action.ActionPerformedEvent event) {
        this.includedFilter.setVisible(true);
    }

    @Subscribe("settingsExcludedBtn.filterExcludedAction")
    private void onSettingsRequest2BtnFilterRequest2Action(Action.ActionPerformedEvent event) {
        this.excludedFilter.setVisible(true);
    }

    /**
     * Самодельное меню выбора видимости колонок
     */
    private void initColumnsMenu() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("Вид строки плана", 0);
        map.put("Наименование", 1);
        map.put("Источник финансирования", 2);
        map.put("Подразделение", 3);
        map.put("Объект инвестиций", 4);
        map.put("Начало", 5);
        map.put("Окончание", 6);
        map.put("Стоимость", 7);
        this.columnsIncludedList.setOptionsMap(map);
        this.columnsExcludedList.setOptionsMap(map);
    }

    @Subscribe("settingsIncludedBtn.columnsIncludedAction")
    private void onSettingsIncludedBtnColumnsIncludedAction(Action.ActionPerformedEvent event) {
        List<DataGrid.Column<PlanVariantTemplateApplication>> visibleColumns = this.includedTable.getVisibleColumns();
        List<DataGrid.Column<PlanVariantTemplateApplication>> columns = this.includedTable.getColumns();
        List<Integer> list = new ArrayList<>();
        // Получаем массив номеров видимых колонок (от 0 до 6)
        for (Integer i = 0; i < columns.size(); i++) {
            if (visibleColumns.contains(columns.get(i))) {
                list.add(i);
            }
        }
        this.columnsIncludedList.setValue(list);

        // Открыть костыльное всплывающее окно со списком колонок
        this.columnsIncludedPopup.setPopupVisible(true);
    }

    @Subscribe("settingsExcludedBtn.columnsExcludedAction")
    private void onSettingsExcludedBtnColumnsExcludedAction(Action.ActionPerformedEvent event) {
        List<DataGrid.Column<PlanVariantTemplateApplication>> visibleColumns = this.excludedTable.getVisibleColumns();
        List<DataGrid.Column<PlanVariantTemplateApplication>> columns = this.excludedTable.getColumns();
        List<Integer> list = new ArrayList<>();
        // Получаем массив номеров видимых колонок (от 0 до 6)
        for (Integer i = 0; i < columns.size(); i++) {
            if (visibleColumns.contains(columns.get(i))) {
                list.add(i);
            }
        }
        this.columnsExcludedList.setValue(list);

        // Открыть костыльное всплывающее окно со списком колонок
        this.columnsExcludedPopup.setPopupVisible(true);
    }

    @Subscribe("columnsIncludedList")
    private void onColumnsIncludedListValueChange(HasValue.ValueChangeEvent event) {
        // После изменения опций в меню скрывает\отображаем нужные колонки таблицы
        Collection<Integer> selected = this.columnsIncludedList.getLookupSelectedItems();
        List<DataGrid.Column<PlanVariantTemplateApplication>> columns = this.includedTable.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).setCollapsed(!selected.contains(i));
        }
    }

    @Subscribe("columnsExcludedList")
    private void onColumnsExcludedListValueChange(HasValue.ValueChangeEvent event) {
        // После изменения опций в меню скрывает\отображаем нужные колонки таблицы
        Collection<Integer> selected = this.columnsExcludedList.getLookupSelectedItems();
        List<DataGrid.Column<PlanVariantTemplateApplication>> columns = this.excludedTable.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).setCollapsed(!selected.contains(i));
        }
    }

    @Subscribe("includedTable.search")
    private void onIncludedTableSearch(Action.ActionPerformedEvent event) {
        String s = this.searchIncludedField.getValue();
        if (s != null) {
            this.planVariantTemplateApplicationsDl.setParameter("s1", s);
            this.planVariantTemplateApplicationsDl.setParameter("s2", s);
        } else {
            this.planVariantTemplateApplicationsDl.removeParameter("s1");
            this.planVariantTemplateApplicationsDl.removeParameter("s2");
        }
        this.planVariantTemplateApplicationsDl.load();
    }

    @Subscribe("excludedTable.search")
    private void onExcludedTableSearch(Action.ActionPerformedEvent event) {
        String s = this.searchExcludedField.getValue();
        if (s != null) {
            this.planVariantTemplateApplications2Dl.setParameter("s1", s);
            this.planVariantTemplateApplications2Dl.setParameter("s2", s);
        } else {
            this.planVariantTemplateApplications2Dl.removeParameter("s1");
            this.planVariantTemplateApplications2Dl.removeParameter("s2");
        }
        this.planVariantTemplateApplications2Dl.load();
    }

    @Subscribe("includedTable.edit")
    private void onIncludedTableEdit(Action.ActionPerformedEvent event) {
        PlanVariantTemplateApplication row = this.includedTable.getSingleSelected();
        this.openEditor(row);
    }

    @Subscribe("excludedTable.edit")
    private void onExcludedTableEdit(Action.ActionPerformedEvent event) {
        PlanVariantTemplateApplication row = this.excludedTable.getSingleSelected();
        this.openEditor(row);
    }

    /**
     * Открыть редактор
     * @param row
     */
    private void openEditor(PlanVariantTemplateApplication row) {
        Application app = row.getApplication();
        if (app instanceof ApplicationIncome) {
            ApplicationIncome edited = this.dataManager.load(ApplicationIncome.class)
                    .id(app.getId()).view("applicationIncome-view-create").one();
            this.screenBuilders.editor(ApplicationIncome.class, this)
                    .editEntity(edited)
                    .withLaunchMode(OpenMode.NEW_WINDOW)
                    .build()
                    .show();
        } else if (app instanceof ApplicationExpense) {
            ApplicationExpense edited = this.dataManager.load(ApplicationExpense.class)
                    .id(app.getId()).view("applicationExpense-view-create").one();
            this.screenBuilders.editor(ApplicationExpense.class, this)
                    .editEntity(edited)
                    .withLaunchMode(OpenMode.NEW_WINDOW)
                    .build()
                    .show();
        } else if (app instanceof ApplicationEvent) {
            ApplicationEvent edited = this.dataManager.load(ApplicationEvent.class)
                    .id(app.getId()).view("applicationEvent-view-full").one();
            this.screenBuilders.editor(ApplicationEvent.class, this)
                    .editEntity(edited)
                    .withLaunchMode(OpenMode.NEW_WINDOW)
                    .build()
                    .show();
        }
    }

    @Subscribe("includedTable")
    private void onIncludedTableItemClick(DataGrid.ItemClickEvent<PlanVariantTemplateApplication> event) {
        PlanVariantTemplateApplication row = event.getItem();
        if (row != null) {
            this.openEditor(row);
        }
    }

    @Subscribe("excludedTable")
    private void onExcludedTableItemClick(DataGrid.ItemClickEvent<PlanVariantTemplateApplication> event) {
        PlanVariantTemplateApplication row = event.getItem();
        if (row != null) {
            this.openEditor(row);
        }
    }

    @Subscribe("includedTable.create")
    private void onIncludedTableCreate(Action.ActionPerformedEvent event) {
        Optional<PlanVariantTemplate> template = ((PlanVariantEdit) this.getHostController()).selectedTemplate;

        this.screenBuilders.screen(this)
                .withScreenClass(Requestnew.class)
                .withAfterCloseListener(requestnewAfterScreenCloseEvent -> {
                    PlanVariantTemplateApplication obj = this.dataManager.create(PlanVariantTemplateApplication.class);
                    obj.setInPlan(true);
                    if (template.isPresent()) {
                        obj.setPlanVariantTemplate(template.get());
                    }

                    if (requestnewAfterScreenCloseEvent.getCloseAction() instanceof RequestNewCloseAction) {
                        // Пользователь выбрал один из вариантов
                        Integer selected = ((RequestNewCloseAction) requestnewAfterScreenCloseEvent.getCloseAction()).selected;
                        switch (selected) {
                            case 1: // Заявка на доходы
                                ApplicationIncome newAppIncome = this.dataManager.create(ApplicationIncome.class);
                                if (template.isPresent()) {
                                    List<PlanVariantTemplateApplication> list = new ArrayList<>();
                                    list.add(obj);
                                    newAppIncome.setPlanVariantTemplateApplications(list);
                                }
                                this.screenBuilders.editor(ApplicationIncome.class, this)
                                        .withScreenClass(ApplicationIncomeEdit.class)
                                        .withOpenMode(OpenMode.NEW_WINDOW)
                                        .newEntity(newAppIncome)
                                        .withAfterCloseListener(applicationIncomeEditAfterScreenCloseEvent -> {
                                            if (applicationIncomeEditAfterScreenCloseEvent.getCloseAction().equals(WINDOW_COMMIT_AND_CLOSE_ACTION)) {
                                                this.planVariantTemplateApplicationsDl.load();
                                            }
                                        })
                                        .build()
                                        .show();
                                break;
                            case 2: // Заявка на расходы
                                ApplicationExpense newAppExpense = this.dataManager.create(ApplicationExpense.class);
                                if (template.isPresent()) {
                                    List<PlanVariantTemplateApplication> list = new ArrayList<>();
                                    list.add(obj);
                                    newAppExpense.setPlanVariantTemplateApplications(list);
                                }
                                this.screenBuilders.editor(ApplicationExpense.class, this)
                                        .withScreenClass(ApplicationExpenseEdit.class)
                                        .withOpenMode(OpenMode.NEW_WINDOW)
                                        .newEntity(newAppExpense)
                                        .withAfterCloseListener(applicationExpenseEditAfterScreenCloseEvent -> {
                                            if (applicationExpenseEditAfterScreenCloseEvent.getCloseAction().equals(WINDOW_COMMIT_AND_CLOSE_ACTION)) {
                                                this.planVariantTemplateApplicationsDl.load();
                                            }
                                        })
                                        .build()
                                        .show();
                                break;
                            case 3: // Заявка на мероприятия
                                ApplicationEvent newAppEvent = this.dataManager.create(ApplicationEvent.class);
                                if (template.isPresent()) {
                                    List<PlanVariantTemplateApplication> list = new ArrayList<>();
                                    list.add(obj);
                                    newAppEvent.setPlanVariantTemplateApplications(list);
                                }
                                this.screenBuilders.editor(ApplicationEvent.class, this)
                                        .withScreenClass(ApplicationEventEdit.class)
                                        .withOpenMode(OpenMode.NEW_WINDOW)
                                        .newEntity(newAppEvent)
                                        .withAfterCloseListener(applicationEventEditAfterScreenCloseEvent -> {
                                            if (applicationEventEditAfterScreenCloseEvent.getCloseAction().equals(WINDOW_COMMIT_AND_CLOSE_ACTION)) {
                                                this.planVariantTemplateApplicationsDl.load();
                                            }
                                        })
                                        .build()
                                        .show();
                                break;
                        }
                    }
                })
                .build()
                .show();
    }

}
