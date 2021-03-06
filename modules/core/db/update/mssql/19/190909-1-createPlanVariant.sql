create table SPMU_PLAN_VARIANT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(255),
    NAME varchar(255),
    DESCRIPTION varchar(max),
    YEAR integer,
    DATE datetime2,
    RESPONSIBLE_ID uniqueidentifier,
    STATUS varchar(50),
    --
    primary key nonclustered (ID)
);