create table SPMU_DIVISION (
    ID uniqueidentifier,
    --
    GUID uniqueidentifier not null,
    PARENT_ID uniqueidentifier,
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    DESCRIPTION varchar(max),
    AFFILIATE_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
);