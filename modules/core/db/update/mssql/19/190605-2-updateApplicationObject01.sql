alter table SPMU_APPLICATION_OBJECT add constraint FK_SPMU_APPLICATION_OBJECT_ON_ORIGIN foreign key (ORIGIN_ID) references SPMU_APPLICATION_OBJECT(ID);
create index IDX_SPMU_APPLICATION_OBJECT_ON_ORIGIN on SPMU_APPLICATION_OBJECT (ORIGIN_ID);