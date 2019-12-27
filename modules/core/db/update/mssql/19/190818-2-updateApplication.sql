alter table SPMU_APPLICATION add DELETED_BY varchar(50) ;
alter table SPMU_APPLICATION add UPDATE_TS datetime2 ;
alter table SPMU_APPLICATION add DELETE_TS datetime2 ;
alter table SPMU_APPLICATION add UPDATED_BY varchar(50) ;
alter table SPMU_APPLICATION add CREATED_BY varchar(50) ;
alter table SPMU_APPLICATION add CREATE_TS datetime2 ;
alter table SPMU_APPLICATION add VERSION integer ^
update SPMU_APPLICATION set VERSION = 0 where VERSION is null ;
alter table SPMU_APPLICATION alter column VERSION integer not null ;
