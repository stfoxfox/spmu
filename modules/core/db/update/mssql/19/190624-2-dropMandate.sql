sp_rename 'SPMU_MANDATE', 'SPMU_MANDATE__U91255' ;
alter table SEC_USER drop constraint FK_SEC_USER_ON_MANDATE ;
alter table SPMU_EMPLOYEE drop constraint FK_SPMU_EMPLOYEE_ON_MANDATE ;
