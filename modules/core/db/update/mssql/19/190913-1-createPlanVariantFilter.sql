create table SPMU_PLAN_VARIANT_FILTER (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PLAN_VARIANT_TEMPLATE_ID uniqueidentifier,
    FILTER_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
);