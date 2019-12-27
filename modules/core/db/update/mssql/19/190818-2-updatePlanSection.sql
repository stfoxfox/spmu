alter table SPMU_PLAN_SECTION add DELETED_BY varchar(50) ;
alter table SPMU_PLAN_SECTION add UPDATE_TS datetime2 ;
alter table SPMU_PLAN_SECTION add DELETE_TS datetime2 ;
alter table SPMU_PLAN_SECTION add UPDATED_BY varchar(50) ;
alter table SPMU_PLAN_SECTION add CREATED_BY varchar(50) ;
alter table SPMU_PLAN_SECTION add CREATE_TS datetime2 ;
alter table SPMU_PLAN_SECTION add VERSION integer ^
update SPMU_PLAN_SECTION set VERSION = 0 where VERSION is null ;
alter table SPMU_PLAN_SECTION alter column VERSION integer not null ;
