exec sp_rename 'SPMU_APPLICATION.PLAN_', 'PLAN___U25921', 'COLUMN' ^
exec sp_rename 'SPMU_APPLICATION.CHANGE', 'CHANGE__U69246', 'COLUMN' ^
alter table SPMU_APPLICATION alter column CHANGE__U69246 varchar(255)  ;
exec sp_rename 'SPMU_APPLICATION.TYPE', 'TYPE__U36019', 'COLUMN' ^
alter table SPMU_APPLICATION alter column TYPE__U36019 varchar(1)  ;
alter table SPMU_APPLICATION add TYPE_ varchar(50) ^
update SPMU_APPLICATION set TYPE_ = 'i' where TYPE_ is null ;
alter table SPMU_APPLICATION alter column TYPE_ varchar(50) not null ;
alter table SPMU_APPLICATION add RESPONSIBLE_ID uniqueidentifier ;
alter table SPMU_APPLICATION add PLAN_ID uniqueidentifier ;
alter table SPMU_APPLICATION add CHANGE tinyint ^
update SPMU_APPLICATION set CHANGE = 0 where CHANGE is null ;
alter table SPMU_APPLICATION alter column CHANGE tinyint not null ;
