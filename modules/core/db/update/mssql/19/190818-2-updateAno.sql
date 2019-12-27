alter table SPMU_ANO add DELETED_BY varchar(50) ;
alter table SPMU_ANO add UPDATE_TS datetime2 ;
alter table SPMU_ANO add DELETE_TS datetime2 ;
alter table SPMU_ANO add UPDATED_BY varchar(50) ;
alter table SPMU_ANO add CREATED_BY varchar(50) ;
alter table SPMU_ANO add CREATE_TS datetime2 ;
alter table SPMU_ANO add VERSION integer ^
update SPMU_ANO set VERSION = 0 where VERSION is null ;
alter table SPMU_ANO alter column VERSION integer not null ;
