alter table SPMU_PLAN_TYPE add constraint FK_SPMU_PLAN_TYPE_ON_CREATOR foreign key (CREATOR_ID) references SEC_USER(ID);
create index IDX_SPMU_PLAN_TYPE_ON_CREATOR on SPMU_PLAN_TYPE (CREATOR_ID);
