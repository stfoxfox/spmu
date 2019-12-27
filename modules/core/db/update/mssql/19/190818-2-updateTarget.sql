alter table SPMU_TARGET add DELETED_BY varchar(50) ;
alter table SPMU_TARGET add UPDATE_TS datetime2 ;
alter table SPMU_TARGET add DELETE_TS datetime2 ;
alter table SPMU_TARGET add UPDATED_BY varchar(50) ;
alter table SPMU_TARGET add CREATED_BY varchar(50) ;
alter table SPMU_TARGET add CREATE_TS datetime2 ;
alter table SPMU_TARGET add VERSION integer ^
update SPMU_TARGET set VERSION = 0 where VERSION is null ;
alter table SPMU_TARGET alter column VERSION integer not null ;
