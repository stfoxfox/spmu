alter table SPMU_WORKFLOW add DELETED_BY varchar(50) ;
alter table SPMU_WORKFLOW add UPDATE_TS datetime2 ;
alter table SPMU_WORKFLOW add DELETE_TS datetime2 ;
alter table SPMU_WORKFLOW add UPDATED_BY varchar(50) ;
alter table SPMU_WORKFLOW add CREATED_BY varchar(50) ;
alter table SPMU_WORKFLOW add CREATE_TS datetime2 ;
alter table SPMU_WORKFLOW add VERSION integer ^
update SPMU_WORKFLOW set VERSION = 0 where VERSION is null ;
alter table SPMU_WORKFLOW alter column VERSION integer not null ;
