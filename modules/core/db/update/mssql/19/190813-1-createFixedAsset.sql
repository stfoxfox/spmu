create table SPMU_FIXED_ASSET (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PARENT_ID uniqueidentifier,
    NAME varchar(255),
    CODE varchar(255),
    FULL_NAME varchar(max),
    VENDOR varchar(255),
    FACTORY_NUMBER varchar(255),
    PASSPORT_NUMBER varchar(255),
    RELEASED_AT datetime2,
    --
    primary key nonclustered (ID)
);