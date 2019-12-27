package com.company.spmu.web.applicationstageevent;

import com.company.spmu.entity.*;
import com.company.spmu.service.AppStageService;
import com.company.spmu.web.applicationevent.ApplicationEventEdit;
import com.company.spmu.web.custom.utils.ResultsByFieldValueUpdateUtils;
import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.export.ByteArrayDataProvider;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstanceLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.yarg.structure.ReportOutputType;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;
import java.util.Calendar;

@UiController("spmu_ApplicationStageEventBrowseFragment")
@UiDescriptor("ApplicationStageEventBrowserFragment.xml")
public class ApplicationStageEventBrowserFragment extends ScreenFragment {
    private ApplicationEvent parentApp;
    private ArrayList<String> generatedColumn;

    @Inject
    private GroupTable<ApplicationStageEvent> applicationStageEventsTable;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private CollectionLoader<ApplicationStageEvent> applicationStageEventsDl;
    @Inject
    private AppStageService appStageService;
    @Inject
    private InstanceLoader<ApplicationEvent> applicationEventDl;
    @Inject
    private CollectionContainer<ApplicationStageEvent> applicationStageEventsDc;
    @Inject
    private ExportDisplay exportDisplay;
    @Inject
    private TextField<String> searchField;
    @Inject
    private UiComponents uiComponents;
    @Inject
    private CheckBoxGroup columnsList;
    @Inject
    private PopupView columnsPopup;
    @Inject
    private Filter filter;
    @Inject
    private EntityStates entityStates;
    @Inject
    private Button createBtn;
    @Inject
    private Metadata metadata;

    /**
     * Кнопка "Копировать"
     * @param {Action.ActionPerformedEvent} event
     */
    @Subscribe("applicationStageEventsTable.copy")
    private void onApplicationStagesTableCopy(Action.ActionPerformedEvent event) {
        Set<ApplicationStageEvent> list = this.applicationStageEventsTable.getSelected();
        for (ApplicationStage a : list) {
            this.appStageService.copy(a);
        }
        this.applicationStageEventsDl.load();
    }

    /**
     * Кнопка "Переместить вверх"
     * @param {Action.ActionPerformedEvent} event
     */
    @Subscribe("applicationStageEventsTable.moveup")
    private void onApplicationStageEventsTableMoveup(Action.ActionPerformedEvent event) {
        Set<ApplicationStageEvent> list = this.applicationStageEventsTable.getSelected();
        for (ApplicationStageEvent a : list) {
            this.appStageService.moveUp(a, this.parentApp);
        }
        this.applicationStageEventsDl.load();
    }

    /**
     * Кнопка "Переместить вниз"
     * @param {Action.ActionPerformedEvent} event
     */
    @Subscribe("applicationStageEventsTable.movedown")
    private void onApplicationStageEventsTableMovedown(Action.ActionPerformedEvent event) {
        Set<ApplicationStageEvent> list = this.applicationStageEventsTable.getSelected();
        for (ApplicationStageEvent a : list) {
            this.appStageService.moveDown(a, this.parentApp);
        }
        this.applicationStageEventsDl.load();
    }

    @Subscribe("applicationStageEventsTable.create")
    private void onApplicationStageEventsTableCreate(Action.ActionPerformedEvent event) {
        ApplicationStageEvent applicationStageEvent = this.metadata.create(ApplicationStageEvent.class);
        applicationStageEvent.setApplication(this.parentApp);
        System.out.println(applicationStageEvent.toString());
        screenBuilders.editor(ApplicationStageEvent.class, this.getFragment().getFrameOwner())
            .withScreenClass(ApplicationStageEventEdit.class)
            .withAfterCloseListener(applicationStageEventEditAfterScreenCloseEvent -> {
                // Сортируем статьи по order
                this.appStageService.recalcAppStageOrder(this.parentApp);

                // Обновляем список статей
                this.applicationStageEventsDl.load();

                // Обновляем заявку
                this.applicationEventDl.load();

                this.recalcLimits();
                this.recalcDates();
            })
            .newEntity(applicationStageEvent)
            .withLaunchMode(OpenMode.NEW_WINDOW)
            .build()
            .show();
    }

    /**
     * Загрузить статьи во вкладке
     * @param parent
     */
    public void setParentApp(ApplicationEvent parent) {
        this.parentApp = parent;

        // Сортируем статьи по order
        this.appStageService.recalcAppStageOrder(this.parentApp);

        // Загружаем статьи
        Map<String, Object> values = new HashMap<>();
        values.put("parentApp", this.parentApp.getId());
        values.put("s1", null);
        values.put("s2", null);
        values.put("s3", null);
        values.put("s4", null);
        values.put("s5", null);
        values.put("s6", null);
        ResultsByFieldValueUpdateUtils.updateResultsByFilterFields(this.applicationStageEventsDl, values);

        if(PersistenceHelper.isNew(parentApp)) {
            this.createBtn.setEnabled(false);
        }
        else {
            this.createBtn.setEnabled(true);
        }
    }

    @Subscribe("applicationStageEventsTable.edit")
    private void onApplicationStageEventsTableEdit(Action.ActionPerformedEvent event) {
        screenBuilders.editor(ApplicationStageEvent.class, this.getFragment().getFrameOwner())
            .withScreenClass(ApplicationStageEventEdit.class)
            .withAfterCloseListener(applicationStageEventEditAfterScreenCloseEvent -> {
                // Обновляем список статей
                this.applicationStageEventsDl.load();

                this.recalcLimits();
                this.recalcDates();

                this.removeGeneratedColumns();
                this.reCreateColumns();
            })
            .editEntity(this.applicationStageEventsTable.getSingleSelected())
            .withLaunchMode(OpenMode.NEW_WINDOW)
            .build()
            .show();
    }

    @Subscribe("applicationStageEventsTable.print")
    private void onApplicationStageEventsTablePrint(Action.ActionPerformedEvent event) {
        byte[] bytes;
        try {
            bytes = this.appStageService.printReportEvent(this.applicationStageEventsDc.getItems());
            this.exportDisplay.show(new ByteArrayDataProvider(bytes), "list.html", ExportFormat.HTML);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe("applicationStageEventsTable.search")
    private void onApplicationStageEventsTableSearch(Action.ActionPerformedEvent event) {
        String search = this.searchField.getValue();
        if (search != "" && search != null) {
            search = "(?i)%" + search + "%";
        } else {
            search = null;
        }

        Integer i;
        try {
            i = Integer.parseInt(search);
        }
        catch (NumberFormatException e) {
            i = null;
        }

        Map<String, Object> values = new HashMap<>();
        values.put("parentApp", this.parentApp.getId());
        values.put("s1", search);
        values.put("s2", search);
        values.put("s3", search);
        values.put("s4", search);
        values.put("s5", search);
        values.put("s6", i);
        ResultsByFieldValueUpdateUtils.updateResultsByFilterFields(this.applicationStageEventsDl, values);
    }

    private void recalcLimits() {
        final Double[] sum = {0.0};
        this.applicationStageEventsDc.getItems().stream().forEach(row -> {
            if (row.getValue() != null) {
                sum[0] += row.getValue();
            }
        });
        ApplicationEventEdit screen = (ApplicationEventEdit) this.getHostScreen();
        screen.saveLimits(sum[0]);
    }

    @Subscribe(target = Target.PARENT_CONTROLLER)
    private void onAfterShow(Screen.AfterShowEvent event) {
        this.recalcLimits();
        this.reCreateColumns();
    }

    /**
     * Пересчёт начальной и конечно даты заявки на основе её этапов
     */
    private void recalcDates() {
        final Date[] dateStart = {null};
        final Date[] dateEnd = {null};
        this.applicationStageEventsDc.getItems().stream().forEach(row -> {
            if (dateStart[0] == null || dateStart[0].after(row.getStart())) {
                dateStart[0] = row.getStart();
            }
            if (dateEnd[0] == null || dateEnd[0].before(row.getEnd())) {
                dateEnd[0] = row.getEnd();
            }
        });
        ApplicationEventEdit screen = (ApplicationEventEdit) this.getHostScreen();
        screen.saveDates(dateStart[0], dateEnd[0]);
    }

    /**
     * Пункт во всплывающем окне "Настроить колонки таблицы"
     */
    @Subscribe("settingsBtn.columnsAction")
    private void onSettingsBtnColumnsAction(Action.ActionPerformedEvent event) {
        List<Table.Column> visibleColumns = this.applicationStageEventsTable.getNotCollapsedColumns();
        List<Table.Column<ApplicationStageEvent>> columns = this.applicationStageEventsTable.getColumns();
        List<Integer> list = new ArrayList<>();
        // Получаем массив номеров видимых колонок (от 0 до 6)
        for (Integer i = 0; i < columns.size(); i++) {
            if (visibleColumns.contains(columns.get(i))) {
                list.add(i);
            }
        }
        this.columnsList.setValue(list);

        // Открыть костыльное всплывающее окно со списком колонок
        this.columnsPopup.setPopupVisible(true);
    }

    /**
     * Пункт во всплывающем окне "Настроить фильтр"
     */
    @Subscribe("settingsBtn.filterAction")
    private void onSettingsBtnFilterAction(Action.ActionPerformedEvent event) {
        this.filter.setVisible(true);
    }

    @Subscribe("columnsList")
    private void onColumnsListValueChange(HasValue.ValueChangeEvent event) {
        // После изменения опций в меню скрывает\отображаем нужные колонки таблицы
        Collection selected = this.columnsList.getLookupSelectedItems();
        List<Table.Column<ApplicationStageEvent>> columns = this.applicationStageEventsTable.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).setCollapsed(!selected.contains(i));
        }
    }

    private void reCreateColumns() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("№ Заявки", 0);
        map.put("Статья ВПО", 1);
        map.put("КУУ", 2);
        map.put("КВУ", 3);
        map.put("Наименование группы доходов", 4);
        map.put("Детализация предмета дохода", 5);
        map.put("Договор №", 6);
        map.put("Общая стоимость", 7);
        Integer n = 8;

        this.generatedColumn = new ArrayList<>();

        if (this.applicationStageEventsDc.getItems().size() > 0) {
            final Calendar c = Calendar.getInstance();
            final int[] years = {0, 0};
            this.applicationStageEventsDc.getItems().stream().forEach(row -> {
                c.setTime(row.getStart());
                int year = c.get(Calendar.YEAR);
                if (years[0] == 0 || year < years[0]) {
                    years[0] = year;
                }

                c.setTime(row.getEnd());
                year = c.get(Calendar.YEAR);
                if (years[1] == 0 || year > years[1]) {
                    years[1] = year;
                }
            });

            if (years[0] > 0 && years[1] > 0 && years[0] <= years[1]) {
                for (int i = years[0]; i <= years[1]; i++) {
                    final int[] yy = {i};
                    map.put(String.valueOf(i), n++);
                    this.generatedColumn.add(String.valueOf(i));
                    this.applicationStageEventsTable.addGeneratedColumn(String.valueOf(i), entity -> {
                        Label label = this.uiComponents.create(Label.class);
                        ApplicationStageValue y1 = entity.getApplicationStageValue();
                        if (y1.getYear() == yy[0]) {
                            label.setValue(String.valueOf(y1.getValue()));
                        } else {
                            final String[] value = {""};
                            y1.getChilds().stream().forEach(child -> {
                                if (child.getYear() == yy[0]) {
                                    value[0] = String.valueOf(child.getValue());
                                }
                            });
                            label.setValue(value[0]);
                        }
                        return label;
                    });
                }
            }
        }

        this.columnsList.setOptionsMap(map);
    }

    private void removeGeneratedColumns() {
        this.generatedColumn.stream().forEach(s -> {
            this.applicationStageEventsTable.removeGeneratedColumn(s);
        });
        this.generatedColumn.clear();
    }

    @Subscribe("exportButton.exportToPdf")
    private void onExportButtonExportToPdf(Action.ActionPerformedEvent event) {
        byte[] bytes;
        try {
            bytes = this.appStageService.exportReportStageEvent(this.applicationStageEventsDc.getItems(), ReportOutputType.pdf);
            this.exportDisplay.show(new ByteArrayDataProvider(bytes), "appstageevents.pdf", ExportFormat.PDF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
