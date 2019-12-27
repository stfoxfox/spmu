alter table SPMU_ANO add constraint FK_SPMU_ANO_ON_AFFILIATE foreign key (AFFILIATE_ID) references SPMU_AFFILATE(ID);
alter table SPMU_ANO add constraint FK_SPMU_ANO_ON_FLIGHT_TYPE foreign key (FLIGHT_TYPE_ID) references SPMU_FLYGHT_TYPE(ID);
create index IDX_SPMU_ANO_ON_AFFILIATE on SPMU_ANO (AFFILIATE_ID);
create index IDX_SPMU_ANO_ON_FLIGHT_TYPE on SPMU_ANO (FLIGHT_TYPE_ID);
