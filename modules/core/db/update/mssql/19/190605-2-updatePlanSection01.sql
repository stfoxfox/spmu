alter table SPMU_PLAN_SECTION add constraint FK_SPMU_PLAN_SECTION_ON_PARENT foreign key (PARENT_ID) references SPMU_PLAN_SECTION(ID);
create index IDX_SPMU_PLAN_SECTION_ON_PARENT on SPMU_PLAN_SECTION (PARENT_ID);