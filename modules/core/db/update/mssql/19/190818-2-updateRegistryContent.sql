alter table SPMU_REGISTRY_CONTENT add DELETED_BY varchar(50) ;
alter table SPMU_REGISTRY_CONTENT add UPDATE_TS datetime2 ;
alter table SPMU_REGISTRY_CONTENT add DELETE_TS datetime2 ;
alter table SPMU_REGISTRY_CONTENT add UPDATED_BY varchar(50) ;
alter table SPMU_REGISTRY_CONTENT add CREATED_BY varchar(50) ;
alter table SPMU_REGISTRY_CONTENT add CREATE_TS datetime2 ;
alter table SPMU_REGISTRY_CONTENT add VERSION integer ^
update SPMU_REGISTRY_CONTENT set VERSION = 0 where VERSION is null ;
alter table SPMU_REGISTRY_CONTENT alter column VERSION integer not null ;
