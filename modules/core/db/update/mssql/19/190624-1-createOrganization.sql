create table SPMU_ORGANIZATION (
    ID uniqueidentifier,
    --
    GUID uniqueidentifier not null,
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    --
    primary key nonclustered (ID)
);