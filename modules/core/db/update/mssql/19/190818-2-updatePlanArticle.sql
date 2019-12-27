alter table SPMU_PLAN_ARTICLE add DELETED_BY varchar(50) ;
alter table SPMU_PLAN_ARTICLE add UPDATE_TS datetime2 ;
alter table SPMU_PLAN_ARTICLE add DELETE_TS datetime2 ;
alter table SPMU_PLAN_ARTICLE add UPDATED_BY varchar(50) ;
alter table SPMU_PLAN_ARTICLE add CREATED_BY varchar(50) ;
alter table SPMU_PLAN_ARTICLE add CREATE_TS datetime2 ;
alter table SPMU_PLAN_ARTICLE add VERSION integer ^
update SPMU_PLAN_ARTICLE set VERSION = 0 where VERSION is null ;
alter table SPMU_PLAN_ARTICLE alter column VERSION integer not null ;
