alter table SPMU_APPLICATION_OBJECT add DELETED_BY varchar(50) ;
alter table SPMU_APPLICATION_OBJECT add UPDATE_TS datetime2 ;
alter table SPMU_APPLICATION_OBJECT add DELETE_TS datetime2 ;
alter table SPMU_APPLICATION_OBJECT add UPDATED_BY varchar(50) ;
alter table SPMU_APPLICATION_OBJECT add CREATED_BY varchar(50) ;
alter table SPMU_APPLICATION_OBJECT add CREATE_TS datetime2 ;
alter table SPMU_APPLICATION_OBJECT add VERSION integer ^
update SPMU_APPLICATION_OBJECT set VERSION = 0 where VERSION is null ;
alter table SPMU_APPLICATION_OBJECT alter column VERSION integer not null ;
