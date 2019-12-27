alter table SPMU_COEFFICIENT_VALUE add DELETED_BY varchar(50) ;
alter table SPMU_COEFFICIENT_VALUE add UPDATE_TS datetime2 ;
alter table SPMU_COEFFICIENT_VALUE add DELETE_TS datetime2 ;
alter table SPMU_COEFFICIENT_VALUE add UPDATED_BY varchar(50) ;
alter table SPMU_COEFFICIENT_VALUE add CREATED_BY varchar(50) ;
alter table SPMU_COEFFICIENT_VALUE add CREATE_TS datetime2 ;
alter table SPMU_COEFFICIENT_VALUE add VERSION integer ^
update SPMU_COEFFICIENT_VALUE set VERSION = 0 where VERSION is null ;
alter table SPMU_COEFFICIENT_VALUE alter column VERSION integer not null ;
