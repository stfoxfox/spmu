alter table SPMU_PLAN_ARTICLE_STAGE add constraint FK_SPMU_PLAN_ARTICLE_STAGE_ON_PLAN_ARTICLE foreign key (PLAN_ARTICLE_ID) references SPMU_PLAN_ARTICLE(ID);
create index IDX_SPMU_PLAN_ARTICLE_STAGE_ON_PLAN_ARTICLE on SPMU_PLAN_ARTICLE_STAGE (PLAN_ARTICLE_ID);