alter table SPMU_APPLICATION_OBJECT add constraint FK_SPMU_APPLICATION_OBJECT_ON_TYPE foreign key (TYPE_ID) references SPMU_APPLICATION_OBJECT_TYPE(ID);
