alter table SPMU_APPLICATION add constraint FK_SPMU_APPLICATION_ON_DIVISION foreign key (DIVISION_ID) references SPMU_DIVISION(ID);
create index IDX_SPMU_APPLICATION_ON_DIVISION on SPMU_APPLICATION (DIVISION_ID);