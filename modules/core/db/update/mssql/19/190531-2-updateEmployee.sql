exec sp_rename 'SPMU_EMPLOYEE.MANDATE_ID', 'MANDATE_ID__U29200', 'COLUMN' ^
alter table SPMU_EMPLOYEE alter column MANDATE_ID__U29200 uniqueidentifier  ;
drop index IDX_SPMU_EMPLOYEE_ON_MANDATE on SPMU_EMPLOYEE ;
alter table SPMU_EMPLOYEE drop constraint FK_SPMU_EMPLOYEE_ON_MANDATE ;
-- alter table SPMU_EMPLOYEE add MANDATE_ID uniqueidentifier ^
-- update SPMU_EMPLOYEE set MANDATE_ID = <default_value> ;
-- alter table SPMU_EMPLOYEE alter column MANDATE_ID uniqueidentifier not null ;
alter table SPMU_EMPLOYEE add MANDATE_ID uniqueidentifier not null ;
