update SPMU_PLAN_ARTICLE_STAGE set IS_CHANGE = 0 where IS_CHANGE is null ;
alter table SPMU_PLAN_ARTICLE_STAGE alter column IS_CHANGE tinyint not null ;
