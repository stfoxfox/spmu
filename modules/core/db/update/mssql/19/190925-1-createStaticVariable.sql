create table SPMU_STATIC_VARIABLE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    COMPUTED_VARIABLE_ID uniqueidentifier,
    VALUE varchar(255),
    POSITION integer,
    --
    primary key nonclustered (ID)
);