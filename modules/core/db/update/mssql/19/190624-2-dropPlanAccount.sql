sp_rename 'SPMU_PLAN_ACCOUNT', 'SPMU_PLAN_ACCOUNT__U04286' ;
alter table SPMU_APPLICATION_STAGE_VALUE drop constraint FK_SPMU_APPLICATION_STAGE_VALUE_ON_PLAN_ACCOUNT ;
