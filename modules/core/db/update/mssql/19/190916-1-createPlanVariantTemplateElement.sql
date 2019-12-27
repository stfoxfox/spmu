create table SPMU_PLAN_VARIANT_TEMPLATE_ELEMENT (
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
    PAGE_ELEMENT varchar(50),
    JSON_VALUE varchar(255),
    --
    primary key nonclustered (ID)
);