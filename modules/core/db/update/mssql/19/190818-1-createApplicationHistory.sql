create table SPMU_APPLICATION_HISTORY (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    ENTITY_SNAPSHOT_ID uniqueidentifier not null,
    PROC_TASK_ID uniqueidentifier,
    USER_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
);