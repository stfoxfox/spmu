alter table SPMU_APPLICATION add constraint FK_SPMU_APPLICATION_ON_ARTICLE foreign key (ARTICLE_ID) references SPMU_PLAN_ARTICLE(ID);
