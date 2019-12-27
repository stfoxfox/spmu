exec sp_rename 'SPMU_REGISTRY_CONTENT.UUID', 'UUID__U22189', 'COLUMN' ^
alter table SPMU_REGISTRY_CONTENT add ID uniqueidentifier ^
update SPMU_REGISTRY_CONTENT set ID = newid() where ID is null ;
alter table SPMU_REGISTRY_CONTENT alter column REGISTRY_ID uniqueidentifier ;
alter table SPMU_REGISTRY_CONTENT alter column APPLICATION_ID uniqueidentifier ;
