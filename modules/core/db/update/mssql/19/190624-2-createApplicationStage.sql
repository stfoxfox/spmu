alter table SPMU_APPLICATION_STAGE add constraint FK_SPMU_APPLICATION_STAGE_ON_PARENT foreign key (PARENT_ID) references SPMU_APPLICATION_STAGE(ID);
alter table SPMU_APPLICATION_STAGE add constraint FK_SPMU_APPLICATION_STAGE_ON_AFFILIATE foreign key (AFFILIATE_ID) references SPMU_AFFILATE(ID);
alter table SPMU_APPLICATION_STAGE add constraint FK_SPMU_APPLICATION_STAGE_ON_OBJECT foreign key (OBJECT_ID) references SPMU_APPLICATION_OBJECT(ID);
alter table SPMU_APPLICATION_STAGE add constraint FK_SPMU_APPLICATION_STAGE_ON_TYPE foreign key (TYPE_ID) references SPMU_APPLICATION_STAGE_TYPE(ID);
alter table SPMU_APPLICATION_STAGE add constraint FK_SPMU_APPLICATION_STAGE_ON_PLAN foreign key (PLAN_ID) references SPMU_PLAN_TYPE(ID);
alter table SPMU_APPLICATION_STAGE add constraint FK_SPMU_APPLICATION_STAGE_ON_SECTION foreign key (SECTION_ID) references SPMU_PLAN_SECTION(ID);
alter table SPMU_APPLICATION_STAGE add constraint FK_SPMU_APPLICATION_STAGE_ON_APPLICATION foreign key (APPLICATION_ID) references SPMU_APPLICATION(ID);
alter table SPMU_APPLICATION_STAGE add constraint FK_SPMU_APPLICATION_STAGE_ON_PLAN_ARTICLE foreign key (PLAN_ARTICLE_ID) references SPMU_PLAN_ARTICLE(ID);
create index IDX_SPMU_APPLICATION_STAGE_ON_PARENT on SPMU_APPLICATION_STAGE (PARENT_ID);
create index IDX_SPMU_APPLICATION_STAGE_ON_AFFILIATE on SPMU_APPLICATION_STAGE (AFFILIATE_ID);
create index IDX_SPMU_APPLICATION_STAGE_ON_OBJECT on SPMU_APPLICATION_STAGE (OBJECT_ID);
create index IDX_SPMU_APPLICATION_STAGE_ON_TYPE on SPMU_APPLICATION_STAGE (TYPE_ID);
create index IDX_SPMU_APPLICATION_STAGE_ON_PLAN on SPMU_APPLICATION_STAGE (PLAN_ID);
create index IDX_SPMU_APPLICATION_STAGE_ON_SECTION on SPMU_APPLICATION_STAGE (SECTION_ID);
create index IDX_SPMU_APPLICATION_STAGE_ON_APPLICATION on SPMU_APPLICATION_STAGE (APPLICATION_ID);
create index IDX_SPMU_APPLICATION_STAGE_ON_PLAN_ARTICLE on SPMU_APPLICATION_STAGE (PLAN_ARTICLE_ID);