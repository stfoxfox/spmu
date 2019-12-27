alter table SPMU_PLAN_ACCOUNT add DELETED_BY varchar(50) ;
alter table SPMU_PLAN_ACCOUNT add UPDATE_TS datetime2 ;
alter table SPMU_PLAN_ACCOUNT add DELETE_TS datetime2 ;
alter table SPMU_PLAN_ACCOUNT add UPDATED_BY varchar(50) ;
alter table SPMU_PLAN_ACCOUNT add CREATED_BY varchar(50) ;
alter table SPMU_PLAN_ACCOUNT add CREATE_TS datetime2 ;
alter table SPMU_PLAN_ACCOUNT add VERSION integer ^
update SPMU_PLAN_ACCOUNT set VERSION = 0 where VERSION is null ;
alter table SPMU_PLAN_ACCOUNT alter column VERSION integer not null ;
