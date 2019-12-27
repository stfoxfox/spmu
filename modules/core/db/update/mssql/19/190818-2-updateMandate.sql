alter table SPMU_MANDATE add DELETED_BY varchar(50) ;
alter table SPMU_MANDATE add UPDATE_TS datetime2 ;
alter table SPMU_MANDATE add DELETE_TS datetime2 ;
alter table SPMU_MANDATE add UPDATED_BY varchar(50) ;
alter table SPMU_MANDATE add CREATED_BY varchar(50) ;
alter table SPMU_MANDATE add CREATE_TS datetime2 ;
alter table SPMU_MANDATE add VERSION integer ^
update SPMU_MANDATE set VERSION = 0 where VERSION is null ;
alter table SPMU_MANDATE alter column VERSION integer not null ;
