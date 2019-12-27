alter table SPMU_FINSOURCE add DELETED_BY varchar(50) ;
alter table SPMU_FINSOURCE add UPDATE_TS datetime2 ;
alter table SPMU_FINSOURCE add DELETE_TS datetime2 ;
alter table SPMU_FINSOURCE add UPDATED_BY varchar(50) ;
alter table SPMU_FINSOURCE add CREATED_BY varchar(50) ;
alter table SPMU_FINSOURCE add CREATE_TS datetime2 ;
alter table SPMU_FINSOURCE add VERSION integer ^
update SPMU_FINSOURCE set VERSION = 0 where VERSION is null ;
alter table SPMU_FINSOURCE alter column VERSION integer not null ;
