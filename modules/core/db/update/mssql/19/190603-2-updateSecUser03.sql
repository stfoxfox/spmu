alter table SEC_USER add constraint FK_SEC_USER_ON_EMPLOYEE foreign key (EMPLOYEE_ID) references SPMU_EMPLOYEE(ID);
create index IDX_SEC_USER_ON_EMPLOYEE on SEC_USER (EMPLOYEE_ID);
