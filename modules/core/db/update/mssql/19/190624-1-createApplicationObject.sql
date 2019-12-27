create table SPMU_APPLICATION_OBJECT (
    ID uniqueidentifier,
    --
    GUID uniqueidentifier,
    ORIGIN_ID uniqueidentifier,
    PARENT_ID uniqueidentifier,
    AFFILIATE_ID uniqueidentifier,
    DIVISION_ID uniqueidentifier,
    RTOPUID uniqueidentifier,
    LOCATION varchar(255),
    LOCATION_ID integer,
    COORDINATE varchar(255),
    NAME varchar(255),
    DESCRIPTION varchar(max),
    TYPE_ID uniqueidentifier,
    LINENUM integer,
    QUANTITY integer,
    --
    primary key nonclustered (ID)
);