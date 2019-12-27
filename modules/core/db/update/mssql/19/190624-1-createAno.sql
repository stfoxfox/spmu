create table SPMU_ANO (
    ID uniqueidentifier,
    --
    DATA_TYPE integer,
    VAL double precision,
    TIME_PERIOD uniqueidentifier,
    AFFILIATE_ID uniqueidentifier,
    FLIGHT_TYPE_ID uniqueidentifier,
    IS_FACT tinyint,
    --
    primary key nonclustered (ID)
);