alter table SPMU_APPLICATION_STAGE_VALUE add constraint FK_SPMU_APPLICATION_STAGE_VALUE_ON_PARENT foreign key (PARENT_ID) references SPMU_APPLICATION_STAGE_VALUE(ID);
create index IDX_SPMU_APPLICATION_STAGE_VALUE_ON_PARENT on SPMU_APPLICATION_STAGE_VALUE (PARENT_ID);