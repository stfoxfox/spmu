exec sp_rename 'SPMU_APPLICATION_STAGE.STAGE_VALUE_ID', 'STAGE_VALUE_ID__U05732', 'COLUMN' ^
drop index IDX_SPMU_APPLICATION_STAGE_ON_STAGE_VALUE on SPMU_APPLICATION_STAGE ;
alter table SPMU_APPLICATION_STAGE drop constraint FK_SPMU_APPLICATION_STAGE_ON_STAGE_VALUE ;
-- alter table SPMU_APPLICATION_STAGE add APPLICATIONSTAGEVALUE uniqueidentifier ^
-- update SPMU_APPLICATION_STAGE set APPLICATIONSTAGEVALUE = <default_value> ;
-- alter table SPMU_APPLICATION_STAGE alter column applicationStageValue uniqueidentifier not null ;
alter table SPMU_APPLICATION_STAGE add APPLICATIONSTAGEVALUE uniqueidentifier not null ;
-- alter table SPMU_APPLICATION_STAGE add APPLICATIONSTAGECONTRACT uniqueidentifier ^
-- update SPMU_APPLICATION_STAGE set APPLICATIONSTAGECONTRACT = <default_value> ;
-- alter table SPMU_APPLICATION_STAGE alter column applicationStageContract uniqueidentifier not null ;
alter table SPMU_APPLICATION_STAGE add APPLICATIONSTAGECONTRACT uniqueidentifier not null ;
alter table SPMU_APPLICATION_STAGE alter column OBJECT_ID uniqueidentifier ;
alter table SPMU_APPLICATION_STAGE alter column APPLICATION_ID uniqueidentifier ;
alter table SPMU_APPLICATION_STAGE alter column PLAN_ARTICLE_ID uniqueidentifier ;
