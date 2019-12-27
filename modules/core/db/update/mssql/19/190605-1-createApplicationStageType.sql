create table SPMU_APPLICATION_STAGE_TYPE (
    ID uniqueidentifier,
    --
    CREATED datetime2,
    EXPIRED datetime2,
    CREATOR_ID uniqueidentifier not null,
    NAME varchar(255) not null,
    FRACTION double precision,
    DURATION integer,
    CALENDAR varchar(50),
    --
    primary key nonclustered (ID)
);