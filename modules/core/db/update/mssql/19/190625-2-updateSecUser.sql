exec sp_rename 'SEC_USER.DTYPE', 'DTYPE__U52158', 'COLUMN' ^
exec sp_rename 'SEC_USER.EMPLOYEE_ID', 'EMPLOYEE_ID__U73969', 'COLUMN' ^
drop index IDX_SEC_USER_ON_EMPLOYEE on SEC_USER ;
alter table SEC_USER drop constraint FK_SEC_USER_ON_EMPLOYEE ;
exec sp_rename 'SEC_USER.MANDATE_ID', 'MANDATE_ID__U31188', 'COLUMN' ^
drop index IDX_SEC_USER_ON_MANDATE on SEC_USER ;
alter table SEC_USER drop constraint FK_SEC_USER_ON_MANDATE ;
exec sp_rename 'SEC_USER.AFFILIATE_ID', 'AFFILIATE_ID__U36633', 'COLUMN' ^
drop index IDX_SEC_USER_ON_AFFILIATE on SEC_USER ;
alter table SEC_USER drop constraint FK_SEC_USER_ON_AFFILIATE ;
