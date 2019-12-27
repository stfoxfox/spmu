alter table SPMU_LOCATION add DELETED_BY varchar(50) ;
alter table SPMU_LOCATION add UPDATE_TS datetime2 ;
alter table SPMU_LOCATION add DELETE_TS datetime2 ;
alter table SPMU_LOCATION add UPDATED_BY varchar(50) ;
alter table SPMU_LOCATION add CREATED_BY varchar(50) ;
alter table SPMU_LOCATION add CREATE_TS datetime2 ;
alter table SPMU_LOCATION add VERSION integer ^
update SPMU_LOCATION set VERSION = 0 where VERSION is null ;
alter table SPMU_LOCATION alter column VERSION integer not null ;
