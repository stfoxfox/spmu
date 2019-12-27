create table SPMU_PLAN_ACCOUNT (
    ID uniqueidentifier,
    --
    GUID uniqueidentifier,
    CODE varchar(32),
    NAME varchar(255),
    ARTICLE_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
);