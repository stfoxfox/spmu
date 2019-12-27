alter table SPMU_ORGANIZATION add DELETED_BY varchar(50) ;
alter table SPMU_ORGANIZATION add UPDATE_TS datetime2 ;
alter table SPMU_ORGANIZATION add DELETE_TS datetime2 ;
alter table SPMU_ORGANIZATION add UPDATED_BY varchar(50) ;
alter table SPMU_ORGANIZATION add CREATED_BY varchar(50) ;
alter table SPMU_ORGANIZATION add CREATE_TS datetime2 ;
alter table SPMU_ORGANIZATION add VERSION integer ^
update SPMU_ORGANIZATION set VERSION = 0 where VERSION is null ;
alter table SPMU_ORGANIZATION alter column VERSION integer not null ;
