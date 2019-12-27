alter table SPMU_CONTRACT add DELETED_BY varchar(50) ;
alter table SPMU_CONTRACT add UPDATE_TS datetime2 ;
alter table SPMU_CONTRACT add DELETE_TS datetime2 ;
alter table SPMU_CONTRACT add UPDATED_BY varchar(50) ;
alter table SPMU_CONTRACT add CREATED_BY varchar(50) ;
alter table SPMU_CONTRACT add CREATE_TS datetime2 ;
alter table SPMU_CONTRACT add VERSION integer ^
update SPMU_CONTRACT set VERSION = 0 where VERSION is null ;
alter table SPMU_CONTRACT alter column VERSION integer not null ;
