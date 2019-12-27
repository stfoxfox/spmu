alter table SPMU_PLAN_TYPE add constraint FK_SPMU_PLAN_TYPE_ON_FUNDING foreign key (FUNDING_ID) references SPMU_FINSOURCE(ID);
