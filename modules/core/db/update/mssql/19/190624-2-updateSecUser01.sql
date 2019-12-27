alter table SEC_USER add constraint FK_SEC_USER_ON_MANDATE foreign key (MANDATE_ID) references SPMU_MANDATE(ID);
create index IDX_SEC_USER_ON_MANDATE on SEC_USER (MANDATE_ID);
