alter table SPMU_APPLICATION_STAGE add constraint FK_SPMU_APPLICATION_STAGE_ON_APPLICATIONSTAGECONTRACT foreign key (APPLICATIONSTAGECONTRACT) references SPMU_APPLICATION_STAGE_CONTRACT(ID);
create index IDX_SPMU_APPLICATION_STAGE_ON_APPLICATIONSTAGECONTRACT on SPMU_APPLICATION_STAGE (APPLICATIONSTAGECONTRACT);
