exec sp_rename 'SPMU_APPLICATION.IS_PSD', 'IS_PSD__U75373', 'COLUMN' ^
exec sp_rename 'SPMU_APPLICATION.IS_GGE', 'IS_GGE__U71727', 'COLUMN' ^
alter table SPMU_APPLICATION alter column KIND varchar(50) ;
alter table SPMU_APPLICATION add IS_GGE tinyint ^
update SPMU_APPLICATION set IS_GGE = 0 where IS_GGE is null ;
alter table SPMU_APPLICATION alter column IS_GGE tinyint not null ;
alter table SPMU_APPLICATION add IS_PSD tinyint ^
update SPMU_APPLICATION set IS_PSD = 0 where IS_PSD is null ;
alter table SPMU_APPLICATION alter column IS_PSD tinyint not null ;
