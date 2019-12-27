create table SPMU_WORKFLOW (
    ID uniqueidentifier,
    --
    GUID uniqueidentifier not null,
    PARENT_ID uniqueidentifier,
    DESCRIPTION varchar(255),
    EQUAL_TO_PROCESS_STAGE_STATUS varchar(255),
    CURRENT_STAGE integer,
    NEW_STAGE integer,
    --
    primary key nonclustered (ID)
);