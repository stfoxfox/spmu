alter table SPMU_AFFILATE add constraint FK_SPMU_AFFILATE_ON_PARENT foreign key (PARENT_ID) references SPMU_AFFILATE(ID);
create index IDX_SPMU_AFFILATE_ON_PARENT on SPMU_AFFILATE (PARENT_ID);
