alter table SPMU_APPLICATION add constraint FK_SPMU_APPLICATION_ON_TARGET foreign key (TARGET_ID) references SPMU_TARGET(ID);
