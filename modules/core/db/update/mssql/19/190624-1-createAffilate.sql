create table SPMU_AFFILATE (
    ID uniqueidentifier,
    --
    GUID uniqueidentifier not null,
    PARENT_ID uniqueidentifier,
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    DESCRIPTION varchar(max),
    FULLNAME varchar(255),
    --
    primary key nonclustered (ID)
);