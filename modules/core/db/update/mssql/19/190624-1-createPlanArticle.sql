create table SPMU_PLAN_ARTICLE (
    ID uniqueidentifier,
    --
    GUID uniqueidentifier,
    PARENT_ID uniqueidentifier,
    FINSOURCE_ID uniqueidentifier,
    NAME varchar(255) not null,
    DESCRIPTION varchar(max),
    FULLNAME varchar(255),
    --
    primary key nonclustered (ID)
);