alter table SPMU_APPLICATION_STAGE_TYPE add DELETED_BY varchar(50) ;
alter table SPMU_APPLICATION_STAGE_TYPE add UPDATE_TS datetime2 ;
alter table SPMU_APPLICATION_STAGE_TYPE add DELETE_TS datetime2 ;
alter table SPMU_APPLICATION_STAGE_TYPE add UPDATED_BY varchar(50) ;
alter table SPMU_APPLICATION_STAGE_TYPE add CREATED_BY varchar(50) ;
alter table SPMU_APPLICATION_STAGE_TYPE add CREATE_TS datetime2 ;
alter table SPMU_APPLICATION_STAGE_TYPE add VERSION integer ^
update SPMU_APPLICATION_STAGE_TYPE set VERSION = 0 where VERSION is null ;
alter table SPMU_APPLICATION_STAGE_TYPE alter column VERSION integer not null ;
