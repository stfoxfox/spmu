alter table SPMU_APPLICATION_HISTORY add constraint FK_SPMU_APPLICATION_HISTORY_ON_ENTITY_SNAPSHOT foreign key (ENTITY_SNAPSHOT_ID) references SYS_ENTITY_SNAPSHOT(ID);
alter table SPMU_APPLICATION_HISTORY add constraint FK_SPMU_APPLICATION_HISTORY_ON_PROC_TASK foreign key (PROC_TASK_ID) references BPM_PROC_TASK(ID);
alter table SPMU_APPLICATION_HISTORY add constraint FK_SPMU_APPLICATION_HISTORY_ON_USER foreign key (USER_ID) references SEC_USER(ID);
create index IDX_SPMU_APPLICATION_HISTORY_ON_ENTITY_SNAPSHOT on SPMU_APPLICATION_HISTORY (ENTITY_SNAPSHOT_ID);
create index IDX_SPMU_APPLICATION_HISTORY_ON_PROC_TASK on SPMU_APPLICATION_HISTORY (PROC_TASK_ID);
create index IDX_SPMU_APPLICATION_HISTORY_ON_USER on SPMU_APPLICATION_HISTORY (USER_ID);