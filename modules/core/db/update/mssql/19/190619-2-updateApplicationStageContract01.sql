alter table SPMU_APPLICATION_STAGE_CONTRACT add constraint FK_SPMU_APPLICATION_STAGE_CONTRACT_ON_STAGE foreign key (STAGE_ID) references SPMU_APPLICATION_STAGE(ID);
create index IDX_SPMU_APPLICATION_STAGE_CONTRACT_ON_STAGE on SPMU_APPLICATION_STAGE_CONTRACT (STAGE_ID);
