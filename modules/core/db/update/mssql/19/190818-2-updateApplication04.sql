alter table SPMU_APPLICATION add constraint FK_SPMU_APPLICATION_ON_PLAN foreign key (PLAN_ID) references SPMU_PLAN_TYPE(ID);
