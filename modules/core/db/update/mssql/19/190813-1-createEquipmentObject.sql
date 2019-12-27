create table SPMU_EQUIPMENT_OBJECT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PARENT_ID uniqueidentifier not null,
    NAME varchar(255),
    code varchar(255),
    IS_EQUIPMENT tinyint,
    GEO_COORDINATE varchar(255),
    AFFILATE_ID uniqueidentifier not null,
    DIVISION_ID uniqueidentifier not null,
    LOCATION_ID uniqueidentifier not null,
    IS_SUBOBJECT tinyint,
    INVENTORY_NUMBER varchar(255),
    FACTORY_NUMBER varchar(255),
    RELEASED_YEAR integer,
    --
    primary key nonclustered (ID)
);