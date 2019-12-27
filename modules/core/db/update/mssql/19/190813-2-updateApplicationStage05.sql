alter table SPMU_APPLICATION_STAGE add constraint FK_SPMU_APPLICATION_STAGE_ON_EQUIPMENT_OBJECT foreign key (EQUIPMENT_OBJECT_ID) references SPMU_EQUIPMENT_OBJECT(ID);
create index IDX_SPMU_APPLICATION_STAGE_ON_EQUIPMENT_OBJECT on SPMU_APPLICATION_STAGE (EQUIPMENT_OBJECT_ID);