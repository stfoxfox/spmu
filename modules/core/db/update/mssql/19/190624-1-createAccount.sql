create table SPMU_ACCOUNT (
    ID uniqueidentifier,
    --
    GUID uniqueidentifier,
    CODE varchar(32),
    NAME varchar(255),
    --
    primary key nonclustered (ID)
);