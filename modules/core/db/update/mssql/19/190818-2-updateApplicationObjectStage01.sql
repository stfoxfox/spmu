alter table SPMU_APPLICATION_OBJECT_STAGE add constraint FK_SPMU_APPLICATION_OBJECT_STAGE_ON_STAGE foreign key (STAGE_ID) references SPMU_APPLICATION_STAGE(ID);
