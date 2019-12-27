alter table SPMU_COEFFICIENT add DELETED_BY varchar(50) ;
alter table SPMU_COEFFICIENT add UPDATE_TS datetime2 ;
alter table SPMU_COEFFICIENT add DELETE_TS datetime2 ;
alter table SPMU_COEFFICIENT add UPDATED_BY varchar(50) ;
alter table SPMU_COEFFICIENT add CREATED_BY varchar(50) ;
alter table SPMU_COEFFICIENT add CREATE_TS datetime2 ;
alter table SPMU_COEFFICIENT add VERSION integer ^
update SPMU_COEFFICIENT set VERSION = 0 where VERSION is null ;
alter table SPMU_COEFFICIENT alter column VERSION integer not null ;
