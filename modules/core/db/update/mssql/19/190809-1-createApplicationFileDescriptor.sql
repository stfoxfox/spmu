create table SPMU_APPLICATION_FILE_DESCRIPTOR (
    ID uniqueidentifier,
    --
    APPLICATION_ID uniqueidentifier,
    FILE_ID uniqueidentifier,
    DESCRIPTION_ varchar(255),
    --
    primary key nonclustered (ID)
);