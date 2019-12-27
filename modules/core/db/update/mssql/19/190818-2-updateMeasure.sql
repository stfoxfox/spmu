alter table SPMU_MEASURE add DELETED_BY varchar(50) ;
alter table SPMU_MEASURE add UPDATE_TS datetime2 ;
alter table SPMU_MEASURE add DELETE_TS datetime2 ;
alter table SPMU_MEASURE add UPDATED_BY varchar(50) ;
alter table SPMU_MEASURE add CREATED_BY varchar(50) ;
alter table SPMU_MEASURE add CREATE_TS datetime2 ;
alter table SPMU_MEASURE add VERSION integer ^
update SPMU_MEASURE set VERSION = 0 where VERSION is null ;
alter table SPMU_MEASURE alter column VERSION integer not null ;
