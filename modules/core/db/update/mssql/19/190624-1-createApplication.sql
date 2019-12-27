create table SPMU_APPLICATION (
    ID uniqueidentifier,
    DTYPE varchar(100),
    --
    TYPE_ varchar(50) not null,
    KIND varchar(50) not null,
    CHANGE tinyint not null,
    ACTIVE varchar(50) not null,
    YEAR integer not null,
    CODE varchar(255) not null,
    DATE datetime2 not null,
    AFFILIATE_ID uniqueidentifier not null,
    DATE_START datetime2,
    DATE_END datetime2,
    RESPONSIBLE_ID uniqueidentifier,
    TARGET_ID uniqueidentifier,
    VALUE double precision not null,
    NAME varchar(255),
    DESCRIPTION varchar(max),
    PLAN_ID uniqueidentifier,
    ARTICLE_ID uniqueidentifier,
    ORDER_ integer,
    LAYOUT integer,
    ASSIGNMENT varchar(255),
    PROOF varchar(255),
    STATUS varchar(50),
    --
    -- from spmu_ApplicationIncome
    PLAN_ARTICLE_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
);