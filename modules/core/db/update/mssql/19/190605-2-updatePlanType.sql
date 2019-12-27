exec sp_rename 'SPMU_PLAN_TYPE.FIXED', 'FIXED__U32582', 'COLUMN' ^
alter table SPMU_PLAN_TYPE alter column FIXED__U32582 varchar(255)  ;
alter table SPMU_PLAN_TYPE add FIXED tinyint ^
update SPMU_PLAN_TYPE set FIXED = 0 where FIXED is null ;
alter table SPMU_PLAN_TYPE alter column FIXED tinyint not null ;
