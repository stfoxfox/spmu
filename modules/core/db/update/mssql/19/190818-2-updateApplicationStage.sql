alter table SPMU_APPLICATION_STAGE add DELETED_BY varchar(50) ;
alter table SPMU_APPLICATION_STAGE add UPDATE_TS datetime2 ;
alter table SPMU_APPLICATION_STAGE add DELETE_TS datetime2 ;
alter table SPMU_APPLICATION_STAGE add UPDATED_BY varchar(50) ;
alter table SPMU_APPLICATION_STAGE add CREATED_BY varchar(50) ;
alter table SPMU_APPLICATION_STAGE add CREATE_TS datetime2 ;
alter table SPMU_APPLICATION_STAGE add VERSION integer ^
update SPMU_APPLICATION_STAGE set VERSION = 0 where VERSION is null ;
alter table SPMU_APPLICATION_STAGE alter column VERSION integer not null ;
