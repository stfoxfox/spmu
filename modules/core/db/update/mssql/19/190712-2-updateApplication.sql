exec sp_rename 'SPMU_APPLICATION.IS_PSD', 'IS_PSD__U10216', 'COLUMN' ^
alter table SPMU_APPLICATION alter column IS_PSD__U10216 tinyint  ;
exec sp_rename 'SPMU_APPLICATION.IS_GGE', 'IS_GGE__U29741', 'COLUMN' ^
alter table SPMU_APPLICATION alter column IS_GGE__U29741 tinyint  ;
update SPMU_APPLICATION set KIND = 'С' where KIND is null ;
alter table SPMU_APPLICATION alter column KIND varchar(50) not null ;
alter table SPMU_APPLICATION add IS_GGE varchar(255) ;
alter table SPMU_APPLICATION add IS_PSD varchar(255) ;
