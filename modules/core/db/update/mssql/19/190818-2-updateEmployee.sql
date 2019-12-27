alter table SPMU_EMPLOYEE add DELETED_BY varchar(50) ;
alter table SPMU_EMPLOYEE add UPDATE_TS datetime2 ;
alter table SPMU_EMPLOYEE add DELETE_TS datetime2 ;
alter table SPMU_EMPLOYEE add UPDATED_BY varchar(50) ;
alter table SPMU_EMPLOYEE add CREATED_BY varchar(50) ;
alter table SPMU_EMPLOYEE add CREATE_TS datetime2 ;
alter table SPMU_EMPLOYEE add VERSION integer ^
update SPMU_EMPLOYEE set VERSION = 0 where VERSION is null ;
alter table SPMU_EMPLOYEE alter column VERSION integer not null ;
