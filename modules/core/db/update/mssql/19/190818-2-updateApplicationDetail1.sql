alter table SPMU_APPLICATION_DETAIL_1 add DELETED_BY varchar(50) ;
alter table SPMU_APPLICATION_DETAIL_1 add UPDATE_TS datetime2 ;
alter table SPMU_APPLICATION_DETAIL_1 add DELETE_TS datetime2 ;
alter table SPMU_APPLICATION_DETAIL_1 add UPDATED_BY varchar(50) ;
alter table SPMU_APPLICATION_DETAIL_1 add CREATED_BY varchar(50) ;
alter table SPMU_APPLICATION_DETAIL_1 add CREATE_TS datetime2 ;
alter table SPMU_APPLICATION_DETAIL_1 add VERSION integer ^
update SPMU_APPLICATION_DETAIL_1 set VERSION = 0 where VERSION is null ;
alter table SPMU_APPLICATION_DETAIL_1 alter column VERSION integer not null ;
