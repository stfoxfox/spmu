alter table SPMU_APPLICATION add constraint FK_SPMU_APPLICATION_ON_AFFILIATE foreign key (AFFILIATE_ID) references SPMU_AFFILATE(ID) on delete CASCADE;
create index IDX_SPMU_APPLICATION_ON_AFFILIATE on SPMU_APPLICATION (AFFILIATE_ID);
