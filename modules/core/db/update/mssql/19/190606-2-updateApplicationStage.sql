-- alter table SPMU_APPLICATION_STAGE add PLAN_ARTICLE_ID uniqueidentifier ^
-- update SPMU_APPLICATION_STAGE set PLAN_ARTICLE_ID = <default_value> ;
-- alter table SPMU_APPLICATION_STAGE alter column PLAN_ARTICLE_ID uniqueidentifier not null ;
alter table SPMU_APPLICATION_STAGE add PLAN_ARTICLE_ID uniqueidentifier not null ;
