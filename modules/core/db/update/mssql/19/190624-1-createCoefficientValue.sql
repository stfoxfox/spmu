create table SPMU_COEFFICIENT_VALUE (
    ID uniqueidentifier,
    --
    COEFFICIENT_ID uniqueidentifier not null,
    PERIOD integer not null,
    VALUE double precision,
    --
    primary key nonclustered (ID)
);