alter table SPMU_AFFILATE add DELETED_BY varchar(50) ;
alter table SPMU_AFFILATE add UPDATE_TS datetime2 ;
alter table SPMU_AFFILATE add DELETE_TS datetime2 ;
alter table SPMU_AFFILATE add UPDATED_BY varchar(50) ;
alter table SPMU_AFFILATE add CREATED_BY varchar(50) ;
alter table SPMU_AFFILATE add CREATE_TS datetime2 ;
alter table SPMU_AFFILATE add VERSION integer ^
update SPMU_AFFILATE set VERSION = 0 where VERSION is null ;
alter table SPMU_AFFILATE alter column VERSION integer not null ;
