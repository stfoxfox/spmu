alter table SPMU_APPLICATION_LINK add constraint FK_SPMU_APPLICATION_LINK_ON_PARENT foreign key (PARENT_ID) references SPMU_APPLICATION(ID);
alter table SPMU_APPLICATION_LINK add constraint FK_SPMU_APPLICATION_LINK_ON_CHILD foreign key (CHILD_ID) references SPMU_APPLICATION(ID);
alter table SPMU_APPLICATION_LINK add constraint FK_SPMU_APPLICATION_LINK_ON_CHILD_STAGE foreign key (CHILD_STAGE_ID) references SPMU_APPLICATION_STAGE(ID);
create index IDX_SPMU_APPLICATION_LINK_ON_PARENT on SPMU_APPLICATION_LINK (PARENT_ID);
create index IDX_SPMU_APPLICATION_LINK_ON_CHILD on SPMU_APPLICATION_LINK (CHILD_ID);
create index IDX_SPMU_APPLICATION_LINK_ON_CHILD_STAGE on SPMU_APPLICATION_LINK (CHILD_STAGE_ID);
