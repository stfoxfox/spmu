package com.company.spmu.web.applicationstageexpense;

import com.company.spmu.entity.*;
import com.company.spmu.repositories.entity.ApplicationStageRepository;
import com.company.spmu.service.AppExpenseService;
import com.company.spmu.web.correctionhistory.ApplicationStageCorrectionHistoryFragment;
import com.haulmont.cuba.core.global.DatatypeFormatter;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstancePropertyContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.Target;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@UiController("spmu_ApplicationStageExpense.edit")
@UiDescriptor("application-stage-expense-edit.xml")
@EditedEntityContainer("applicationStageExpenseDc")
@LoadDataBeforeShow
@DialogMode(forceDialog = true, resizable = true, closeOnClickOutside = true)
public class ApplicationStageExpenseEdit extends StandardEditor<ApplicationStageExpense> {
    private boolean isNew = false;
    private ApplicationStageExpense original;
    @Inject
    private TextField<Double> q4Field;
    @Inject
    private TextField<Double> q3Field;
    @Inject
    private TextField<Double> q1Field;
    @Inject
    private TextField<Double> q2Field;
    @Inject
    protected Metadata metadata;
    @Inject
    protected InstancePropertyContainer<ApplicationStageValue> applicationStageValueDc;

    @Inject
    protected InstancePropertyContainer<ApplicationStageContract> applicationStageContractDc;

    @Inject
    protected DataContext dataContext;

    @Inject
    private ScreenBuilders screenBuilders;

    @Inject
    private DatatypeFormatter formatter;
    @Inject
    protected Label titleLabel;
    @Inject
    private AppExpenseService appExpenseService;
    @Inject
    private HBoxLayout months;
    @Inject
    private HBoxLayout quarters;
    @Inject
    private Button showMonths;
    @Inject
    private TextField<Double> m10Field;
    @Inject
    private TextField<Double> m11Field;
    @Inject
    private TextField<Double> m12Field;
    @Inject
    private TextField<Double> m1Field;
    @Inject
    private TextField<Double> m2Field;
    @Inject
    private TextField<Double> m3Field;
    @Inject
    private TextField<Double> m4Field;
    @Inject
    private TextField<Double> m5Field;
    @Inject
    private TextField<Double> m6Field;
    @Inject
    private TextField<Double> m7Field;
    @Inject
    private TextField<Double> m8Field;
    @Inject
    private TextField<Double> m9Field;
    @Inject
    private TextField<Double> totalField;
    @Inject
    private TextField<Double> year4;
    @Inject
    private TextField<Double> year3;
    @Inject
    private TextField<Double> year2;
    @Inject
    private TextField<Double> year1;
    @Inject
    private TextField<Double> totalForAllYearsField;

    //для установки и управления дочерними значениями
    private List<ApplicationStageValue> applicationStageValues;

    @Subscribe
    protected void onInitEntity(InitEntityEvent<ApplicationStageExpense> event) {
        this.year1.setValue(0.0);
        this.year2.setValue(0.0);
        this.year3.setValue(0.0);
        this.year4.setValue(0.0);
        ApplicationStageValue applicationStageValue = createApplicationStageValue();
        applicationStageValue.setApplicationStage(event.getEntity());
        event.getEntity().setQ1(0.0);
        event.getEntity().setQ2(0.0);
        event.getEntity().setQ3(0.0);
        event.getEntity().setQ4(0.0);
        event.getEntity().setM1(0.0);
        event.getEntity().setM2(0.0);
        event.getEntity().setM3(0.0);
        event.getEntity().setM4(0.0);
        event.getEntity().setM5(0.0);
        event.getEntity().setM6(0.0);
        event.getEntity().setM7(0.0);
        event.getEntity().setM8(0.0);
        event.getEntity().setM9(0.0);
        event.getEntity().setM10(0.0);
        event.getEntity().setM11(0.0);
        event.getEntity().setM12(0.0);
        event.getEntity().setApplicationStageValue(applicationStageValue);

        ApplicationStageContract createApplicationStageContract = createApplicationStageContract();
        createApplicationStageContract.setStage(event.getEntity());
        event.getEntity().setApplicationStageContract(createApplicationStageContract);
    }

    @Subscribe
    private void onAfterShow(AfterShowEvent event) {
        this.totalField.addValidator(value -> {
            Double sumQuarters = this.sumAll();

            if(!value.equals(sumQuarters)){
                throw new ValidationException("Общая стоимость должна совпадать с суммой кварталов и лет.");
            }
        });
        this.q1Field.addValidator(value -> {
            Double sumMonths = this.m1Field.getValue() + this.m2Field.getValue() + this.m3Field.getValue();
            if(!value.equals(sumMonths) && sumMonths!=0){
                throw new ValidationException("Сумма 1-го квартала должна совпадать с суммой месяцев.");
            }
        });
        this.q2Field.addValidator(value -> {
            Double sumMonths = this.m4Field.getValue() + this.m5Field.getValue() + this.m6Field.getValue();
            if(!value.equals(sumMonths) && sumMonths!=0){
                throw new ValidationException("Сумма 2-го квартала должна совпадать с суммой месяцев.");
            }
        });
        this.q3Field.addValidator(value -> {
            Double sumMonths = this.m7Field.getValue() + this.m8Field.getValue() + this.m9Field.getValue();
            if(!value.equals(sumMonths) && sumMonths!=0){
                throw new ValidationException("Сумма 3-го квартала должна совпадать с суммой месяцев.");
            }
        });
        this.q4Field.addValidator(value -> {
            Double sumMonths = this.m10Field.getValue() + this.m11Field.getValue() + this.m12Field.getValue();
            if(!value.equals(sumMonths) && sumMonths!=0){
                throw new ValidationException("Сумма 4-го квартала должна совпадать с суммой месяцев.");
            }
        });

        this.q1Field.addValueChangeListener(this::valueChangeListener);
        this.q2Field.addValueChangeListener(this::valueChangeListener);
        this.q3Field.addValueChangeListener(this::valueChangeListener);
        this.q4Field.addValueChangeListener(this::valueChangeListener);

        this.m1Field.addValueChangeListener(this::quater1ChangeListener);
        this.m2Field.addValueChangeListener(this::quater1ChangeListener);
        this.m3Field.addValueChangeListener(this::quater1ChangeListener);
        this.m4Field.addValueChangeListener(this::quater2ChangeListener);
        this.m5Field.addValueChangeListener(this::quater2ChangeListener);
        this.m6Field.addValueChangeListener(this::quater2ChangeListener);
        this.m7Field.addValueChangeListener(this::quater3ChangeListener);
        this.m8Field.addValueChangeListener(this::quater3ChangeListener);
        this.m9Field.addValueChangeListener(this::quater3ChangeListener);
        this.m10Field.addValueChangeListener(this::quater4ChangeListener);
        this.m11Field.addValueChangeListener(this::quater4ChangeListener);
        this.m12Field.addValueChangeListener(this::quater4ChangeListener);

        this.year1.addValueChangeListener(this::valueChangeListener);
        this.year2.addValueChangeListener(this::valueChangeListener);
        this.year3.addValueChangeListener(this::valueChangeListener);
        this.year4.addValueChangeListener(this::valueChangeListener);
        this.totalForAllYearsField.addValueChangeListener(this::valueChangeListener);
    }
    private void quater1ChangeListener(HasValue.ValueChangeEvent<Double> value) {
        this.forNull(value);
        this.q1Field.setValue(this.m3Field.getValue() + this.m2Field.getValue() + this.m1Field.getValue());
        this.totalField.setValue(this.sumAll());
    }
    private void quater2ChangeListener(HasValue.ValueChangeEvent<Double> value) {
        this.forNull(value);
        this.q2Field.setValue(this.m6Field.getValue() + this.m5Field.getValue() + this.m4Field.getValue());
        this.totalField.setValue(this.sumAll());
    }

    private void quater3ChangeListener(HasValue.ValueChangeEvent<Double> value) {
        this.forNull(value);
        this.q3Field.setValue(this.m9Field.getValue() + this.m8Field.getValue() + this.m7Field.getValue());
        this.totalField.setValue(this.sumAll());
    }
    private void quater4ChangeListener(HasValue.ValueChangeEvent<Double> value) {
        this.forNull(value);
        this.q4Field.setValue(this.m12Field.getValue() + this.m11Field.getValue() + this.m10Field.getValue());
        this.totalField.setValue(this.sumAll());
    }
    private void forNull(HasValue.ValueChangeEvent<Double> value){
        if (value.getValue() == null) {
            value.getComponent().setValue(0.0);
        }
    }
    private void valueChangeListener(HasValue.ValueChangeEvent<Double> value) {
        this.forNull(value);
        this.totalField.setValue(this.sumAll());
    }

    private ApplicationStageValue createApplicationStageValue() {
        ApplicationStageValue applicationStageValue = dataContext.merge(metadata.create(ApplicationStageValue.class));
        applicationStageValue.setValue(0.0);
        return applicationStageValue;
    }

    private ApplicationStageContract createApplicationStageContract() {
        return dataContext.merge(metadata.create(ApplicationStageContract.class));
    }

    @Subscribe
    private void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        ApplicationStageValue applicationStageValue = this.getEditedEntity().getApplicationStageValue();
        applicationStageValue.setQ1(this.getEditedEntity().getQ1());
        applicationStageValue.setQ2(this.getEditedEntity().getQ2());
        applicationStageValue.setQ3(this.getEditedEntity().getQ3());
        applicationStageValue.setQ4(this.getEditedEntity().getQ4());
        applicationStageValue.setM1(this.getEditedEntity().getM1());
        applicationStageValue.setM2(this.getEditedEntity().getM2());
        applicationStageValue.setM3(this.getEditedEntity().getM3());
        applicationStageValue.setM4(this.getEditedEntity().getM4());
        applicationStageValue.setM5(this.getEditedEntity().getM5());
        applicationStageValue.setM6(this.getEditedEntity().getM6());
        applicationStageValue.setM7(this.getEditedEntity().getM7());
        applicationStageValue.setM8(this.getEditedEntity().getM8());
        applicationStageValue.setM9(this.getEditedEntity().getM9());
        applicationStageValue.setM10(this.getEditedEntity().getM10());
        applicationStageValue.setM11(this.getEditedEntity().getM11());
        applicationStageValue.setM12(this.getEditedEntity().getM12());
        //для "Запланированно в предшествующие годы"
        this.applicationStageValues.get(0).setValue(this.totalForAllYearsField.getValue());
        this.applicationStageValues.get(0).setYear(this.getEditedEntity().getApplication().getYear()-1);
        this.dataContext.merge(this.applicationStageValues.get(0)).setParent(this.getEditedEntity().getApplicationStageValue());


        this.applicationStageValues.get(1).setValue(this.year1.getValue());
        this.applicationStageValues.get(2).setValue(this.year2.getValue());
        this.applicationStageValues.get(3).setValue(this.year3.getValue());
        this.applicationStageValues.get(4).setValue(this.year4.getValue());
        for (int i=1; i<5; i++) { // установка значений в parent и установка значения в year, также сохранение данных
            this.applicationStageValues.get(i).setYear(this.getEditedEntity().getApplication().getYear()+i);
            this.dataContext.merge(this.applicationStageValues.get(i)).setParent(applicationStageValue);
        }
        this.dataContext.commit();
        this.getEditedEntity().getApplication().setValue(this.appExpenseService.recalcStagesSum(this.getEditedEntity().getApplication()));
    }


    @Subscribe(target = Target.DATA_CONTEXT)
    private void onPreCommit(DataContext.PreCommitEvent event) {
        this.getEditedEntity().setValue(this.sumAll());
        int year = this.getEditedEntity().getApplication().getYear();
        Date dateStart = Date.from(LocalDate.of(year, 1, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date dateEnd = Date.from(LocalDate.of(year, 12, 31).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        this.getEditedEntity().setStart(dateStart);
        this.getEditedEntity().setEnd(dateEnd);
    }

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        this.isNew = PersistenceHelper.isNew(this.getEditedEntity());
        this.original = this.getEditedEntity();
        Application application = this.getEditedEntity().getApplication();
        if (!this.isNew) {
            // Заголовки
            String title = "Статья ";
            title += ((this.original.getOrder() != null ? " №" + this.original.getOrder() : "") +"заявки на текущие расходы" + (application.getCode() != null ? " №" + application.getCode() : ""));
            titleLabel.setValue(title);
            this.getWindow().setCaption("Статья"+(this.original.getOrder() != null ? " №" + this.original.getOrder() : "") +"заявки на текущие расходы" + (application.getCode() != null ? " №" + application.getCode() : ""));
        }
        else  {
            String title = "Создание статьи заявки на расходы";
            titleLabel.setValue(title);
        }
        this.applicationStageValues = this.appExpenseService.getParentList(this.getEditedEntity().getApplicationStageValue());
        if(this.applicationStageValues.isEmpty()){
            this.applicationStageValues = new ArrayList<>();
            for (int i=0; i<5; i++) {
                this.applicationStageValues.add(i, createApplicationStageValue());
            }
        }
        //генерация лет
        this.year1.setCaption(application.getYear()+1+"");
        this.year2.setCaption(application.getYear()+2+"");
        this.year3.setCaption(application.getYear()+3+"");
        this.year4.setCaption(application.getYear()+4+"");
        // установка значений при иницализации
        this.totalForAllYearsField.setValue(this.applicationStageValues.get(0).getValue());
        this.year1.setValue(this.applicationStageValues.get(1).getValue());
        this.year2.setValue(this.applicationStageValues.get(2).getValue());
        this.year3.setValue(this.applicationStageValues.get(3).getValue());
        this.year4.setValue(this.applicationStageValues.get(4).getValue());

    }

    @Subscribe("showMonths")
    private void onShowMonthsClick(Button.ClickEvent event) {
        if (this.months.isVisible()){
            this.months.setVisible(false);
            this.quarters.setVisible(true);
            this.showMonths.setCaption("Показать месяца");
        } else {
            this.months.setVisible(true);
            this.quarters.setVisible(false);
            this.showMonths.setCaption("Показать кварталы");
        }
    }

    private double sumAll(){
        return this.q1Field.getValue() + this.q2Field.getValue()
                + this.q3Field.getValue() + this.q4Field.getValue() + this.year1.getValue()
                + this.year2.getValue() + this.year3.getValue() + this.year4.getValue()
                + this.totalForAllYearsField.getValue();
    }

    @Subscribe("changesBtn")
    private void onChangesBtnClick(Button.ClickEvent event) {

        ApplicationStageCorrectionHistoryFragment applicationHistoryBrowse = screenBuilders.screen(this)
                .withScreenClass(ApplicationStageCorrectionHistoryFragment.class)
                .withLaunchMode(OpenMode.NEW_TAB)
                .build();

        applicationHistoryBrowse.setEntity(this.getEditedEntity());
        applicationHistoryBrowse.show();
    }
}
