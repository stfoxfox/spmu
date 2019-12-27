alter table SPMU_REGISTRY add DELETED_BY varchar(50) ;
alter table SPMU_REGISTRY add UPDATE_TS datetime2 ;
alter table SPMU_REGISTRY add DELETE_TS datetime2 ;
alter table SPMU_REGISTRY add UPDATED_BY varchar(50) ;
alter table SPMU_REGISTRY add CREATED_BY varchar(50) ;
alter table SPMU_REGISTRY add CREATE_TS datetime2 ;
alter table SPMU_REGISTRY add VERSION integer ^
update SPMU_REGISTRY set VERSION = 0 where VERSION is null ;
alter table SPMU_REGISTRY alter column VERSION integer not null ;
