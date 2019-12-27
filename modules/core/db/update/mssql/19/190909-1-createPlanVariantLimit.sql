create table SPMU_PLAN_VARIANT_LIMIT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PLAN_VARIANT_ID uniqueidentifier,
    YEAR integer,
    VALUE double precision not null,
    --
    primary key nonclustered (ID)
);