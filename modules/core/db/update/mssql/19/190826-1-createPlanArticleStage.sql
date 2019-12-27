create table SPMU_PLAN_ARTICLE_STAGE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PLAN_ARTICLE_ID uniqueidentifier,
    NAME varchar(255),
    IS_CHANGE tinyint,
    --
    primary key nonclustered (ID)
);