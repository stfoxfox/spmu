sp_rename 'SPMU_PLAN_TYPE', 'SPMU_PLAN_TYPE__U21050' ;
alter table SPMU_APPLICATION drop constraint FK_SPMU_APPLICATION_ON_PLAN ;
alter table SPMU_APPLICATION_STAGE drop constraint FK_SPMU_APPLICATION_STAGE_ON_PLAN ;
alter table SPMU_PLAN_SECTION drop constraint FK_SPMU_PLAN_SECTION_ON_TYPE ;