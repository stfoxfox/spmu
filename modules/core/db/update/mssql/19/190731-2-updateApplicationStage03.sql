alter table SPMU_APPLICATION_STAGE add IS_QUARTAL tinyint ^
update SPMU_APPLICATION_STAGE set IS_QUARTAL = 0 where IS_QUARTAL is null ;
alter table SPMU_APPLICATION_STAGE alter column IS_QUARTAL tinyint not null ;
