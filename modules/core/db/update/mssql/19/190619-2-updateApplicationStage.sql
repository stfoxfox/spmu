exec sp_rename 'SPMU_APPLICATION_STAGE.applicationStageContract', 'applicationStageContract__U98040', 'COLUMN' ^
alter table SPMU_APPLICATION_STAGE alter column applicationStageContract__U98040 uniqueidentifier  ;
drop index IDX_SPMU_APPLICATION_STAGE_ON_APPLICATIONSTAGECONTRACT on SPMU_APPLICATION_STAGE ;
alter table SPMU_APPLICATION_STAGE drop constraint FK_SPMU_APPLICATION_STAGE_ON_APPLICATIONSTAGECONTRACT ;
exec sp_rename 'SPMU_APPLICATION_STAGE.applicationStageValue', 'applicationStageValue__U77944', 'COLUMN' ^
alter table SPMU_APPLICATION_STAGE alter column applicationStageValue__U77944 uniqueidentifier  ;
drop index IDX_SPMU_APPLICATION_STAGE_ON_APPLICATIONSTAGEVALUE on SPMU_APPLICATION_STAGE ;
alter table SPMU_APPLICATION_STAGE drop constraint FK_SPMU_APPLICATION_STAGE_ON_APPLICATIONSTAGEVALUE ;
