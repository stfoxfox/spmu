create table SPMU_APPLICATION_STAGE_LINK (
    ID uniqueidentifier,
    --
    PARENT_ID uniqueidentifier,
    CHILD_ID uniqueidentifier,
    CHILD_STAGE_ID uniqueidentifier,
    LINKTYPE varchar(50),
    STEP integer,
    --
    primary key nonclustered (ID)
);