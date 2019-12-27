update SPMU_PLAN_ARTICLE_STAGE set NAME = '' where NAME is null ;
alter table SPMU_PLAN_ARTICLE_STAGE alter column NAME varchar(255) not null ;
