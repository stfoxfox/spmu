package com.company.spmu.web.applicationstageevent;

import com.company.spmu.entity.*;
import com.company.spmu.service.AppStageService;
import com.company.spmu.web.applicationstagefiledescriptor.ApplicationStageFileDescriptorEdit;
import com.company.spmu.web.applicationstagevalue.ApplicationStageValueEdit;
import com.company.spmu.web.correctionhistory.ApplicationStageCorrectionHistoryFragment;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.company.spmu.entity.Application;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.time.ZoneId;
import java.util.Date;

@UiController("spmu_ApplicationStageEvent.edit")
@UiDescriptor("application-stage-event-edit.xml")
@EditedEntityContainer("applicationStageEventDc")
@LoadDataBeforeShow
public class ApplicationStageEventEdit extends StandardEditor<ApplicationStageEvent> {
    private int num = 1;
    private int num1 = 1;
    private boolean isNew = false;

    @Inject
    private FileUploadField uploadField;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private CollectionLoader<ApplicationStageFileDescriptor> applicationStageFileDescriptorsLc;
    @Inject
    private DataManager dataManager;
    @Inject
    private Table<ApplicationStageFileDescriptor> filesTable;
    @Inject
    private Metadata metadata;
    @Inject
    private TextField<String> sumField;
    @Inject
    private CollectionLoader<ApplicationStage> stagesLc;
    @Inject
    private CheckBox chVidRabot;
    @Inject
    private CheckBox chZayMeropr;
    @Inject
    private LookupField<ApplicationStageType> typeField;
    @Inject
    private LookupField<ApplicationStage> parentField;
    @Inject
    private CheckBox objectCh;
    @Inject
    private CheckBox object2Ch;
    @Inject
    private LookupField<EquipmentObject> object2Field;
    @Inject
    private LookupField<ApplicationObject> objectField;
    @Inject
    private CheckBox isMonthField;
    @Inject
    private CheckBox isQuartalField;
    @Inject
    private ExportDisplay exportDisplay;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private UiComponents uiComponents;
    @Inject
    private CollectionContainer<ApplicationStageFileDescriptor> applicationStageFileDescriptorsDc;
    @Inject
    private LookupPickerField<Employee> curatorField;
    @Inject
    private TextField<String> curatorPost;
    @Inject
    private LookupPickerField<Employee> managerField;
    @Inject
    private TextField<String> managerPost;
    @Inject
    private TextField<Integer> planPeriod;
    @Inject
    private TextField<Integer> year;
    @Inject
    private AppStageService appStageService;
    @Inject
    private TabSheet tabsheet;
    @Inject
    private DateField<Date> startField;
    @Inject
    private DateField<Date> endField;
    @Inject
    private CollectionLoader<ApplicationStageValue> applicationStageValuesDl2;
    @Inject
    private CollectionContainer<ApplicationStageValue> applicationStageValueDc2;
    @Inject
    private Table<ApplicationStageValue> limitsMTable;
    @Inject
    private Table<ApplicationStageValue> limitsQTable;
    @Inject
    private TextField<Double> valueField;
    @Inject
    protected Label labelTitle;

    @Subscribe("curatorField")
    private void onCuratorFieldValueChange(HasValue.ValueChangeEvent<Employee> event) {
        Employee e = event.getValue();
        if (e == null) {
            this.curatorPost.setValue("");
        } else {
            Position p = e.getPosition();
            if (p == null) {
                this.curatorPost.setValue("");
            } else {
                this.curatorPost.setValue(p.getName());
            }
        }
    }

    @Subscribe("managerField")
    private void onManagerFieldValueChange(HasValue.ValueChangeEvent<Employee> event) {
        Employee e = event.getValue();
        if (e == null) {
            this.managerPost.setValue("");
        } else {
            Position p = e.getPosition();
            if (p == null) {
                this.managerPost.setValue("");
            } else {
                this.managerPost.setValue(p.getName());
            }
        }
    }

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        this.isNew = (this.getEditedEntity() == null || PersistenceHelper.isNew(this.getEditedEntity()));
        if (this.isNew ) {
            this.stagesLc.removeParameter("id");
        } else {
            this.stagesLc.setParameter("id", this.getEditedEntity().getId());
        }

        this.applicationStageValuesDl2.setParameter("app", this.getEditedEntity());
        this.applicationStageValuesDl2.setParameter("parent", this.getEditedEntity().getApplicationStageValue());
        this.applicationStageValuesDl2.load();
        this.applicationStageFileDescriptorsLc.setParameter("appf", this.getEditedEntity());

        Application application = this.dataManager.reload(this.getEditedEntity(), "applicationStageEvent-edit").getApplication();
        if (!this.isNew) {
            String title = "Этап ";
            title += ((this.getEditedEntity().getOrder() != null ? " №" + this.getEditedEntity().getOrder() : "") +" заявки на мероприятие" + (application.getCode() != null ? " №" + application.getCode() : ""));
            labelTitle.setValue(title);
            this.getWindow().setCaption("Этап " + (this.getEditedEntity().getOrder()!= null ? " №" + this.getEditedEntity().getOrder() : "") +" заявки на мероприятие" + (application.getCode() != null ? " №" + application.getCode() : ""));
        }
        else{
            String title = "Создание этапа заявки на мероприятие";
            labelTitle.setValue(title);
        }


    }

    @Subscribe
    private void onInit(InitEvent event) {
        this.sumField.setValue("в тыс. руб.");

        this.uploadField.addFileUploadSucceedListener(uploadSucceedEvent -> {
            File file = this.fileUploadingAPI.getFile(this.uploadField.getFileId());
            if (file != null) {
                FileDescriptor fd = this.uploadField.getFileDescriptor();
                try {
                    this.fileUploadingAPI.putFileIntoStorage(this.uploadField.getFileId(), fd);
                } catch (FileStorageException e) {
                    throw new RuntimeException("Error saving file to FileStorage", e);
                }
                this.dataManager.commit(fd);
                ApplicationStageFileDescriptor aFile = this.metadata.create(ApplicationStageFileDescriptor.class);
                aFile.setFile(fd);
                aFile.setApplication(this.getEditedEntity());
                this.dataManager.commit(aFile);
                this.applicationStageFileDescriptorsLc.load();
            }
        });

        this.uploadField.addFileUploadErrorListener(uploadErrorEvent -> {
            System.out.println(uploadErrorEvent.toString());
        });

        this.num = 1;

        this.startField.addValidator(date -> {
            if (date == null) {
                throw new ValidationException("Поле '" + this.startField.getCaption() + "' не заполнено");
            }
        });
        this.endField.addValidator(date -> {
            if (date == null) {
                throw new ValidationException("Поле '" + this.endField.getCaption() + "' не заполнено");
            }
            if (this.startField.getValue() != null) {
                if (this.startField.getValue().after(date)) {
                    throw new ValidationException("Поле '" + this.startField.getCaption() + "' не может быть больше чем поле '" + this.endField.getCaption() + "'");
                }
            }
        });

        this.limitsMTable.setItemClickAction(new BaseAction("dblclick") {
            public void actionPerform(Component component) {
                ApplicationStageValue value = limitsMTable.getSingleSelected();
                if (value != null) {
                    openLimitsEditor(value);
                }
            }
        });
        this.limitsQTable.setItemClickAction(new BaseAction("dblclick2") {
            public void actionPerform(Component component) {
                ApplicationStageValue value = limitsQTable.getSingleSelected();
                if (value != null) {
                    openLimitsEditor(value);
                }
            }
        });
    }

    @Subscribe("file_delete")
    private void onFile_delete(Action.ActionPerformedEvent event) {
        ApplicationStageFileDescriptor selected = this.filesTable.getSingleSelected();
        this.dataManager.remove(selected);
        this.applicationStageFileDescriptorsLc.load();
    }

    @Subscribe("file_open")
    private void onFile_open(Action.ActionPerformedEvent event) {
        ApplicationStageFileDescriptor selected = this.filesTable.getSingleSelected();
        if (selected != null) {
            this.screenBuilders.editor(ApplicationStageFileDescriptor.class, this)
                    .withScreenClass(ApplicationStageFileDescriptorEdit.class).newEntity(selected)
                    .withLaunchMode(OpenMode.DIALOG)
                    .withAfterCloseListener(applicationFileDescriptorEditAfterScreenCloseEvent -> {
                        this.applicationStageFileDescriptorsLc.load();
                    })
                    .build()
                    .show();
        }
    }

    @Subscribe("file_save")
    private void onFile_save(Action.ActionPerformedEvent event) {
        ApplicationStageFileDescriptor selected = this.filesTable.getSingleSelected();
        if (selected != null) {
            this.exportDisplay.show(selected.getFile(), ExportFormat.OCTET_STREAM);
        }
    }

    @Subscribe
    private void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if (!this.chVidRabot.getValue()) {
            this.typeField.setValue(null);
        }
        if (!this.chZayMeropr.getValue()) {
            this.parentField.setValue(null);
        }

        if (!this.objectCh.getValue()) {
            this.objectField.setValue(null);
        }
        if (!this.object2Ch.getValue()) {
            this.object2Field.setValue(null);
        }
        this.getEditedEntity().setIs_quartal(this.isQuartalField.getValue());
    }

    @Subscribe
    private void onAfterShow(AfterShowEvent event) {
        if (this.typeField.getValue() != null) {
            this.chVidRabot.setValue(true);
        } else {
            this.typeField.setEditable(false);
        }
        if (this.parentField.getValue() != null) {
            this.chZayMeropr.setValue(true);
        } else {
            this.parentField.setEditable(false);
        }

        if (this.objectField.getValue() != null) {
            this.objectCh.setValue(true);
        } else {
            this.objectField.setEditable(false);
        }
        if (this.object2Field.getValue() != null) {
            this.object2Ch.setValue(true);
        } else {
            this.object2Field.setEditable(false);
        }

        Boolean a = this.getEditedEntity().getIs_quartal();
        this.isQuartalField.setValue(a);
        this.isMonthField.setValue(!a);

        ApplicationStageEvent ae = this.getEditedEntity();
        ApplicationStageResponsible ai;

        // Установка значения поле "Ответственный за мероприятие в ГД"
        ai = ae.getApplicationStageResponsibleCurator();
        if (ai != null) {
            this.curatorField.setValue(ai.getEmployee());
        }

        // Установка значения поле "Ответственный за мероприятие в филиале"
        ai = ae.getApplicationStageResponsibleManager();
        if (ai != null) {
            this.managerField.setValue(ai.getEmployee());
        }

        if (this.isNew) {
            this.tabsheet.getTab("tab4").setEnabled(false);
        }

        setFieldValuesFromApplication();
    }

    private void setFieldValuesFromApplication() {
        Application application = getEditedEntity().getApplication();
        Date dateStart = application.getDateStart();
        if (dateStart != null) {
            int startDateYear = dateStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
            this.planPeriod.setValue(startDateYear);
        }
        this.year.setValue(application.getYear());
    }

    @Subscribe("chVidRabot")
    private void onChVidRabotValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.chZayMeropr.setValue(false);
            this.typeField.setEditable(true);
        } else {
            this.typeField.setEditable(false);
        }
    }

    @Subscribe("chZayMeropr")
    private void onChZayMeroprValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.chVidRabot.setValue(false);
            this.parentField.setEditable(true);
        } else {
            this.parentField.setEditable(false);
        }
    }

    @Subscribe("objectCh")
    private void onObjectChValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.object2Ch.setValue(false);
            this.objectField.setEditable(true);
        } else {
            this.objectField.setEditable(false);
        }
    }

    @Subscribe("object2Ch")
    private void onObject2ChValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.objectCh.setValue(false);
            this.object2Field.setEditable(true);
        } else {
            this.object2Field.setEditable(false);
        }
    }

    @Subscribe("isMonthField")
    private void onIsMonthFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.isQuartalField.setValue(false);
            this.limitsMTable.setVisible(true);
            this.limitsQTable.setVisible(false);
            this.saveQM();
        } else {
            this.isQuartalField.setValue(true);
        }
    }

    @Subscribe("isQuartalField")
    private void onIsQuartalFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            this.isMonthField.setValue(false);
            this.limitsMTable.setVisible(false);
            this.limitsQTable.setVisible(true);
            this.saveQM();
        } else {
            this.isMonthField.setValue(true);
        }
    }

    /**
     * Генерация нумерации для таблицы файлов
     * @param entity
     * @return
     */
    public Component fileNumGenerate(Entity entity) {
        Label label = this.uiComponents.create(Label.class);
        label.setValue(this.num);
        this.num++;
        if (this.num > this.applicationStageFileDescriptorsDc.getMutableItems().size()) {
            this.num = 1;
        }
        return label;
    }

    @Subscribe
    private void onAfterCommitChanges(AfterCommitChangesEvent event) {
        ApplicationStage appEvent = this.getEditedEntity();
        Employee employee;

        // Сохранение "Ответственный за мероприятие в ГД"
        employee = this.curatorField.getValue();
        if (employee != null) {
            this.appStageService.saveCurator(appEvent, employee);
        }

        // Сохранение "Ответственный за мероприятие в филиале"
        employee = this.managerField.getValue();
        if (employee != null) {
            this.appStageService.saveManager(appEvent, employee);
        }

        // Создаём\изменяем лимиты в зависимости от даты начала-конца
        final java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(appEvent.getStart());
        int startYear = c.get(java.util.Calendar.YEAR);
        c.setTime(appEvent.getEnd());
        int endYear = c.get(java.util.Calendar.YEAR);

        if (startYear > 1900 && endYear > 1900 && endYear < 3000 && startYear <= endYear) {
            if (this.isNew) {
                ApplicationStageValue value;
                value = this.dataManager.create(ApplicationStageValue.class);
                value.setYear(startYear);
                value.setApplicationStage(appEvent);
                this.dataManager.commit(value);
                for (int i = startYear + 1; i <= endYear; i++) {
                    ApplicationStageValue value2 = this.dataManager.create(ApplicationStageValue.class);
                    value2.setYear(i);
                    value2.setParent(value);
                    this.dataManager.commit(value2);
                }
            } else {
                ApplicationStageValue pa = this.dataManager.load(ApplicationStageValue.class).id(this.getEditedEntity().getApplicationStageValue().getId()).view("applicationStageValue-edit").one();
                final int[] maxYear = {0};
                // Удаляем лишние года с конца
                pa.getChilds().stream().forEach(row -> {
                    if (row.getYear() > endYear) {
                        this.dataManager.remove(row);
                    } else if (row.getYear() > maxYear[0]) {
                        maxYear[0] = row.getYear();
                    }
                });
                // Создаёт недостающие года в конце
                if (maxYear[0] > 0) {
                    for (int i = maxYear[0] + 1; i <= endYear; i++) {
                        ApplicationStageValue value = this.dataManager.create(ApplicationStageValue.class);
                        value.setYear(i);
                        value.setParent(pa);
                        this.dataManager.commit(value);
                    }
                }

                if (startYear != this.getEditedEntity().getApplicationStageValue().getYear()) {
                    // Поменялся начальный год
                    if (startYear < this.getEditedEntity().getApplicationStageValue().getYear()) {
                        // Год стал меньше
                        ApplicationStageValue old = this.getEditedEntity().getApplicationStageValue();
                        this.dataManager.reload(old, "applicationStageValue-edit");
                        old.setApplicationStage(null);
                        this.dataManager.commit(old);

                        ApplicationStageValue value;
                        value = this.dataManager.create(ApplicationStageValue.class);
                        value.setYear(startYear);
                        value.setApplicationStage(appEvent);
                        this.dataManager.commit(value);

                        this.getEditedEntity().setApplicationStageValue(value);
                        this.dataManager.commit(this.getEditedEntity());

                        old.setParent(value);
                        this.dataManager.commit(old);

                        for (int i = startYear + 1; i < old.getYear(); i++) {
                            ApplicationStageValue value2 = this.dataManager.create(ApplicationStageValue.class);
                            value2.setYear(i);
                            value2.setParent(value);
                            this.dataManager.commit(value2);
                        }

                        old.getChilds().stream().forEach(row -> {
                            row.setParent(value);
                            this.dataManager.commit(row);
                        });

                        //this.setEntityModified(false);

                    } else {
                        // Год стал больше
                        ApplicationStageValue old = this.dataManager.load(ApplicationStageValue.class).id(this.getEditedEntity().getApplicationStageValue().getId()).view("applicationStageValue-edit").one();
                        final ApplicationStageValue[] newParent = new ApplicationStageValue[1];
                        old.setApplicationStage(null);
                        this.dataManager.commit(old);

                        old.getChilds().stream().forEach(row -> {
                            if (row.getYear() == startYear) {
                                newParent[0] = row;
                            }
                        });

                        old.getChilds().stream().forEach(row -> {
                            if (row.getYear() < startYear) {
                                this.dataManager.remove(row);
                            } else if (row.getYear() > startYear) {
                                row.setParent(newParent[0]);
                                this.dataManager.commit(row);
                            }
                        });
                        ApplicationStageValue vv = this.dataManager.load(ApplicationStageValue.class).id(newParent[0].getId()).view("applicationStageValue-edit").one();
                        newParent[0] = null;
                        vv.setParent(null);
                        vv.setApplicationStage(this.getEditedEntity());
                        this.dataManager.commit(vv);
                        this.getEditedEntity().setApplicationStageValue(vv);
                        this.dataManager.commit(this.getEditedEntity());

                        ApplicationStageValue old2 = this.dataManager.load(ApplicationStageValue.class).id(old.getId()).view("applicationStageValue-edit").one();
                        this.dataManager.remove(old2);

                        //this.setEntityModified(false);
                    }
                }

            }
        }
    }

    /**
     * Генерация нумерации для таблицы лимитов
     * @param entity
     * @return
     */
    public Component limitTableNumGenerate(Entity entity) {
        Label label = this.uiComponents.create(Label.class);
        label.setValue(this.num1);
        this.num1++;
        if (this.num1 > this.applicationStageValueDc2.getMutableItems().size()) {
            this.num1 = 1;
        }
        return label;
    }

    /**
     * Открывание редактора лимита финансирования этапа мероприятия
     * @param selected
     */
    private void openLimitsEditor(ApplicationStageValue selected) {
        ApplicationStageValue edited = this.dataManager.load(ApplicationStageValue.class).id(selected.getId()).view("applicationStageValue-edit").one();
        this.screenBuilders.editor(ApplicationStageValue.class, this)
                .withScreenClass(ApplicationStageValueEdit.class)
                .editEntity(edited)
                .withLaunchMode(OpenMode.NEW_WINDOW)
                .withAfterCloseListener(applicationFileDescriptorEditAfterScreenCloseEvent -> {
                    this.applicationStageValuesDl2.load();
                    this.recalcValue();
                })
                .build()
                .show();
    }

    private void saveQM() {
        if (!this.isNew) {
            this.getEditedEntity().setIs_quartal(this.isQuartalField.getValue());
            this.dataManager.commit(this.getEditedEntity());

            // Сумма месяцев\кварталов в году/лимите
            this.applicationStageValueDc2.getItems().stream().forEach(row -> {
                Double sum = 0.0;
                try {
                    if (this.isQuartalField.getValue()) {
                        sum += row.getQ1() + row.getQ2() + row.getQ3() + row.getQ4();
                    } else {
                        sum += row.getM1() + row.getM2() + row.getM3() + row.getM4() + row.getM5() + row.getM6() + row.getM7() + row.getM8() + row.getM9() + row.getM10()
                                + row.getM11() + row.getM12();
                    }
                } catch (NullPointerException e) {
                }
                row.setValue(sum);
                this.dataManager.commit(row);
            });
            this.recalcValue();
        }
    }

    /**
     * Пересчёт поля "Стоимость Этапа"
     */
    private void recalcValue() {
        final Double[] sum = {0.0};
        // Сумма стоимостей годов-лимитов
        this.applicationStageValueDc2.getItems().stream().forEach(row -> {
            sum[0] += row.getValue();
        });
        ApplicationStageEvent e = this.dataManager.load(ApplicationStageEvent.class).id(this.getEditedEntity().getId()).one();
        if (e != null) {
            e.setValue(sum[0]);
           this.dataManager.commit(e);
        }
        this.valueField.setValue(sum[0]);
    }

    @Subscribe("changesBtn")
    private void onChangesBtnClick(Button.ClickEvent event) {
        ApplicationStageCorrectionHistoryFragment correctionHistoryFragment = screenBuilders.screen(this)
                .withScreenClass(ApplicationStageCorrectionHistoryFragment.class)
                .withLaunchMode(OpenMode.NEW_TAB)
                .build();

        correctionHistoryFragment.setEntity(this.getEditedEntity());
        correctionHistoryFragment.show();
    }
}
