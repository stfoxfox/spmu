alter table SPMU_APPLICATION_LINK add DELETED_BY varchar(50) ;
alter table SPMU_APPLICATION_LINK add UPDATE_TS datetime2 ;
alter table SPMU_APPLICATION_LINK add DELETE_TS datetime2 ;
alter table SPMU_APPLICATION_LINK add UPDATED_BY varchar(50) ;
alter table SPMU_APPLICATION_LINK add CREATED_BY varchar(50) ;
alter table SPMU_APPLICATION_LINK add CREATE_TS datetime2 ;
alter table SPMU_APPLICATION_LINK add VERSION integer ^
update SPMU_APPLICATION_LINK set VERSION = 0 where VERSION is null ;
alter table SPMU_APPLICATION_LINK alter column VERSION integer not null ;
