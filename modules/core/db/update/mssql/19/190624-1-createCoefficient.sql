create table SPMU_COEFFICIENT (
    ID uniqueidentifier,
    --
    CODE varchar(32),
    TYPE integer,
    NAME varchar(255),
    MEASURE_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
);