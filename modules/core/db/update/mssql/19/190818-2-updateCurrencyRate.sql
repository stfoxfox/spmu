alter table SPMU_CURRENCY_RATE add DELETED_BY varchar(50) ;
alter table SPMU_CURRENCY_RATE add UPDATE_TS datetime2 ;
alter table SPMU_CURRENCY_RATE add DELETE_TS datetime2 ;
alter table SPMU_CURRENCY_RATE add UPDATED_BY varchar(50) ;
alter table SPMU_CURRENCY_RATE add CREATED_BY varchar(50) ;
alter table SPMU_CURRENCY_RATE add CREATE_TS datetime2 ;
alter table SPMU_CURRENCY_RATE add VERSION integer ^
update SPMU_CURRENCY_RATE set VERSION = 0 where VERSION is null ;
alter table SPMU_CURRENCY_RATE alter column VERSION integer not null ;
