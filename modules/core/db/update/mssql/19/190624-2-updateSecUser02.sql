alter table SEC_USER add constraint FK_SEC_USER_ON_AFFILIATE foreign key (AFFILIATE_ID) references SPMU_AFFILATE(ID);
create index IDX_SEC_USER_ON_AFFILIATE on SEC_USER (AFFILIATE_ID);
