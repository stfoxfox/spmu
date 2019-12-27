alter table SPMU_DIVISION add DELETED_BY varchar(50) ;
alter table SPMU_DIVISION add UPDATE_TS datetime2 ;
alter table SPMU_DIVISION add DELETE_TS datetime2 ;
alter table SPMU_DIVISION add UPDATED_BY varchar(50) ;
alter table SPMU_DIVISION add CREATED_BY varchar(50) ;
alter table SPMU_DIVISION add CREATE_TS datetime2 ;
alter table SPMU_DIVISION add VERSION integer ^
update SPMU_DIVISION set VERSION = 0 where VERSION is null ;
alter table SPMU_DIVISION alter column VERSION integer not null ;
