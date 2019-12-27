create table SPMU_CONNECTION_LOG (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    URL varchar(255),
    REQUEST varchar(255),
    HTTP_CODE integer,
    RESPONSE varchar(255),
    --
    primary key nonclustered (ID)
);