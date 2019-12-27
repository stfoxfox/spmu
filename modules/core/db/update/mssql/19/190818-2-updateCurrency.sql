alter table SPMU_CURRENCY add DELETED_BY varchar(50) ;
alter table SPMU_CURRENCY add UPDATE_TS datetime2 ;
alter table SPMU_CURRENCY add DELETE_TS datetime2 ;
alter table SPMU_CURRENCY add UPDATED_BY varchar(50) ;
alter table SPMU_CURRENCY add CREATED_BY varchar(50) ;
alter table SPMU_CURRENCY add CREATE_TS datetime2 ;
alter table SPMU_CURRENCY add VERSION integer ^
update SPMU_CURRENCY set VERSION = 0 where VERSION is null ;
alter table SPMU_CURRENCY alter column VERSION integer not null ;
