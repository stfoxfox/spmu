create table SPMU_APPLICATION_OBJECT_STAGE (
    ID uniqueidentifier,
    --
    OBJECT_ID uniqueidentifier not null,
    STAGE_ID uniqueidentifier not null,
    ORDER_ integer not null,
    --
    primary key nonclustered (ID)
);