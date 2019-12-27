alter table SPMU_APPLICATION_STAGE_VALUE add DELETED_BY varchar(50) ;
alter table SPMU_APPLICATION_STAGE_VALUE add UPDATE_TS datetime2 ;
alter table SPMU_APPLICATION_STAGE_VALUE add DELETE_TS datetime2 ;
alter table SPMU_APPLICATION_STAGE_VALUE add UPDATED_BY varchar(50) ;
alter table SPMU_APPLICATION_STAGE_VALUE add CREATED_BY varchar(50) ;
alter table SPMU_APPLICATION_STAGE_VALUE add CREATE_TS datetime2 ;
alter table SPMU_APPLICATION_STAGE_VALUE add VERSION integer ^
update SPMU_APPLICATION_STAGE_VALUE set VERSION = 0 where VERSION is null ;
alter table SPMU_APPLICATION_STAGE_VALUE alter column VERSION integer not null ;
