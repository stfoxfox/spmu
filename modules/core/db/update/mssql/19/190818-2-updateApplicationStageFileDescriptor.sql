alter table SPMU_APPLICATION_STAGE_FILE_DESCRIPTOR add DELETED_BY varchar(50) ;
alter table SPMU_APPLICATION_STAGE_FILE_DESCRIPTOR add UPDATE_TS datetime2 ;
alter table SPMU_APPLICATION_STAGE_FILE_DESCRIPTOR add DELETE_TS datetime2 ;
alter table SPMU_APPLICATION_STAGE_FILE_DESCRIPTOR add UPDATED_BY varchar(50) ;
alter table SPMU_APPLICATION_STAGE_FILE_DESCRIPTOR add CREATED_BY varchar(50) ;
alter table SPMU_APPLICATION_STAGE_FILE_DESCRIPTOR add CREATE_TS datetime2 ;
alter table SPMU_APPLICATION_STAGE_FILE_DESCRIPTOR add VERSION integer ^
update SPMU_APPLICATION_STAGE_FILE_DESCRIPTOR set VERSION = 0 where VERSION is null ;
alter table SPMU_APPLICATION_STAGE_FILE_DESCRIPTOR alter column VERSION integer not null ;