alter table SPMU_APPLICATION_DETAIL_1 add constraint FK_SPMU_APPLICATION_DETAIL_1_ON_APPLICATION foreign key (APPLICATION_ID) references SPMU_APPLICATION(ID);
alter table SPMU_APPLICATION_DETAIL_1 add constraint FK_SPMU_APPLICATION_DETAIL_1_ON_TYPE foreign key (TYPE_ID) references SPMU_APPLICATION_DETAIL(ID);
create index IDX_SPMU_APPLICATION_DETAIL_1_ON_APPLICATION on SPMU_APPLICATION_DETAIL_1 (APPLICATION_ID);
create index IDX_SPMU_APPLICATION_DETAIL_1_ON_TYPE on SPMU_APPLICATION_DETAIL_1 (TYPE_ID);
