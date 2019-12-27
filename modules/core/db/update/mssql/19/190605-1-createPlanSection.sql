create table SPMU_PLAN_SECTION (
    ID uniqueidentifier,
    --
    GUID uniqueidentifier,
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    PARENT_ID uniqueidentifier not null,
    TYPE_ID uniqueidentifier,
    CREATED datetime2,
    EXPIRED datetime2,
    CREATOR_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
);