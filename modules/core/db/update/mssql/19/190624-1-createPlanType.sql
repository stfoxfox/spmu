create table SPMU_PLAN_TYPE (
    ID uniqueidentifier,
    --
    GUID uniqueidentifier not null,
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    DESCRIPTION varchar(max),
    KIND varchar(50) not null,
    FIXED tinyint not null,
    FUNDING_ID uniqueidentifier not null,
    CREATED datetime2 not null,
    EXPIRED datetime2 not null,
    CREATOR_ID uniqueidentifier not null,
    --
    primary key nonclustered (ID)
);